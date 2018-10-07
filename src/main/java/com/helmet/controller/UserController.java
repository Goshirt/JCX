package com.helmet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.helmet.entity.Log;
import com.helmet.entity.Menu;
import com.helmet.entity.Role;
import com.helmet.entity.User;
import com.helmet.service.LogService;
import com.helmet.service.MenuService;
import com.helmet.service.RoleService;
import com.helmet.service.UserService;
import com.helmet.util.StringUtil;



/**
 * 用户controller
 * 
 * @author Helmet
 * 2018年5月30日
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 
	 * @param imageCode
	 * @param user  /@valid是springMvc支持的验证，需要在user类中的属性使用注解定义每种属性的规定约束，提交数据是就会使用ajax交互，将结果返回
	 * @param bindingResult 验证user绑定的结果返回的信息
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(String imageCode,@Valid User user,BindingResult bindingResult,HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		
		//判断用户输入的验证是否为空
		if (StringUtil.isEmpty(imageCode)) {
			map.put("success", false);
			map.put("errorInfo", "验证码不能为空");
			return map;
		}
		
		//获取系统生成的验证码
		String checkCode=(String) session.getAttribute("checkcode");
		//判断用户输入的验证码是否正确
		if (StringUtil.isNotEmpty(checkCode)) {
			if (!checkCode.equals(imageCode)) {
				map.put("success", false);
				map.put("errorInfo", "验证码输入错误");
				return map;
			}
		}
		
		//判断验证用户的姓名及密码的输入框是否有定义的错误信息
		if (bindingResult.hasErrors()) {
			map.put("success", false);
			map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
			return map;
		}
		
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try {
			//shiro登录身份验证
			subject.login(token);
			//获取登录的用户名
			String userName=(String) SecurityUtils.getSubject().getPrincipal();
			User currentUser=userService.getUserByUserName(userName);
			List<Role> roles=roleService.getRolesByUserId(currentUser.getUserId());
			session.setAttribute("currentUser", currentUser);
			logService.log(new Log(Log.LOGIN_ACTION, "用户进行了登录"));
			map.put("roles", roles);
			map.put("size", roles.size());
			map.put("success", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "用户名或者密码错误");
			return map;
		}
	}
	
	/**
	 *保存该用户用户选择登录的角色 
	 * @param roleId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	public Map<String, Object> saveRole(Integer roleId,HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		Role currentRole=roleService.getRoleByRoleId(roleId);
		session.setAttribute("currentRole", currentRole);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 加载当前用户和角色信息
	 * @param session
	 * @return 返回欢迎当前用户和角色的字符串
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping("/loadUserInfo")
	public String loadUserInfo(HttpSession session) throws Exception{
		User user=(User) session.getAttribute("currentUser");
		Role role=(Role) session.getAttribute("currentRole");
		return "欢迎您:"+user.getUserName()+"&nbsp;[&nbsp;<font color='red'>"+role.getRoleName()+"</font>]";
	}
	
	
	/**
	 * 加载角色权限树
	 * @param session
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping("/loadMenuInfo")
	public String loadMenuInfo(HttpSession session,Integer parentId) throws Exception{
		Role role=(Role) session.getAttribute("currentRole");
		return loadMenuTree(role.getRoleId(), parentId).toString();
	}
	
	/**
	 * 获取parentId下的子节点，只是一层
	 * @param roleId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public JsonArray loadMenuById(Integer roleId,Integer parentId)throws Exception{
		List<Menu> menus=menuService.getMenuByRoleId(parentId, roleId);
		JsonArray menuArray=new JsonArray();
		for (Menu menu:menus) {
			JsonObject oneMenu=new JsonObject();
			//封装easyui树的数据格式，key是固定写法
			oneMenu.addProperty("id", menu.getMenuId());
			oneMenu.addProperty("text", menu.getMenuName());
			if (menu.getState()==1) {
				oneMenu.addProperty("state", "closed");
			}else {
				oneMenu.addProperty("state", "open");
			}
			oneMenu.addProperty("iconCls", menu.getIcon());
			JsonObject attrObject=new JsonObject();
			//添加每个菜单的url,attributes是easyui树的扩展属性，用来添加一些扩展的属性，里边也是一个jsonObject数据格式
			attrObject.addProperty("url", menu.getUrl());
			oneMenu.add("attributes", attrObject);
			menuArray.add(oneMenu);
		}
		return menuArray;
	}
	
	/**
	 * 递归生成整棵菜单树
	 * @param roleId
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public JsonArray loadMenuTree(Integer roleId,Integer parentId )throws Exception{
		//获取该用户角色的第一层菜单
		JsonArray menuArray=this.loadMenuById(roleId, parentId);
		for (int i = 0; i < menuArray.size(); i++) {
			//获取该层菜单的每个节点信息
			JsonObject jsonObject=(JsonObject) menuArray.get(i);
			//如果该节点状态是open,证明没有孩子节点，该节点结束
			if ("open".equals(jsonObject.get("state"))) {
				continue;
			}else {
				//如果该节点状态是关闭，证明有孩子节点，给该节点添加一个children属性（固定写法），把该节点当做父节点，遍历他的孩子节点，递归生成整棵树
				jsonObject.add("children", loadMenuTree(roleId, jsonObject.get("id").getAsInt()));
			}
		}
		return menuArray;
	}
}
