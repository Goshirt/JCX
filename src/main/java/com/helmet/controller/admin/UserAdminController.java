package com.helmet.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helmet.entity.Log;
import com.helmet.entity.Role;
import com.helmet.entity.User;
import com.helmet.entity.UserRole;
import com.helmet.service.LogService;
import com.helmet.service.RoleService;
import com.helmet.service.UserRoleService;
import com.helmet.service.UserService;
import com.helmet.util.StringUtil;


@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 用户管理页面 datagrid获取数据
	 * @RequiresPermissions 拥有value指定的菜单权限才能访问该方法
	 * @param user
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/userList")
	@RequiresPermissions(value="用户管理")
	public Map<String, Object> getUserList(User user,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize) {
		Map<String, Object> resultMap=new HashMap<>();
		List<User> userList=userService.getUserList(user, page, pageSize, Direction.ASC, "id");
		Long count=userService.countUser(user);
		for (User user2 : userList) {
			//获取拥有的角色集
			List<Role> roleList=roleService.getRolesByUserId(user2.getUserId());
			StringBuffer sb=new StringBuffer();
			for (Role role : roleList) {
				//按,隔开的格式拼接角色名
				if (StringUtil.isNotEmpty(role.getRoleName())) {
					sb.append(","+role.getRoleName());
				}
			}
			user2.setHaveRole(sb.toString().replaceFirst(",", ""));
			
		}
		logService.log(new Log(Log.SELECT_ACTION, ""
				+ "获取了用户信息集合"));
		resultMap.put("rows", userList);
		resultMap.put("total", count);
		return resultMap;
	}
	
	
	/**
	 * 添加或者修改用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value="用户管理")
	public Map<String, Object> save(User user)throws Exception{
		Map<String, Object> resultMap=new HashMap<>();
		//如果传入的用户Id为空，则证明是添加操作
		if (user.getUserId()==null) {
			//通过用户名获取数据库中是否已经存在该用户
			User haveUser=userService.getUserByUserName(user.getUserName());
			//如果用户已经存在
			if (haveUser!=null) {
				resultMap.put("success", false);
				resultMap.put("errorInfo", "该用户名已经存在");
				return resultMap;
				
			}
		}
		if (user!=null) {
			if (user.getUserId()==null) {
				logService.log(new Log(Log.UPDATE_ACTION, "新增了一个用户"+user.toString()));
			}else {
				logService.log(new Log(Log.UPDATE_ACTION, "更改了用户信息"+user.toString()));
			}
		}
		userService.saveUser(user);
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 删除用户
	 * @param deleteId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="用户管理")
	public Map<String, Object> delete(Integer deleteId) throws Exception{
		Map<String, Object> resultMap=new HashMap<>();
		userRoleService.deleteByUserId(deleteId);
		logService.log(new Log(Log.DELETE_ACTION, "删除了一个用户"+userService.getUserById(deleteId).toString()));
		userService.deleteUser(deleteId);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 保存给指定用户授予的角色
	 * @param roleIds
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	@RequiresPermissions(value="用户管理")
	public Map<String, Object> saveRole(@RequestParam(value="roleIds",required=true)String roleIds,@RequestParam(value="userId",required=true)Integer userId){
		Map<String, Object> resultMap=new HashMap<>();
		//需要先删除之前存在的用户角色关联关系
		userRoleService.deleteByUserId(userId);
		if (StringUtil.isNotEmpty(roleIds)) {
			String[] roleIdStr=roleIds.split(",");
			for (int i = 0; i < roleIdStr.length; i++) {
				UserRole userRole=new UserRole();
				userRole.setRole(roleService.getRoleByRoleId(Integer.valueOf(roleIdStr[i])));
				userRole.setUser(userService.getUserById(userId));
				userRoleService.save(userRole);
			}
		}
		logService.log(new Log(Log.UPDATE_ACTION, "更新了用户拥有的权限"+userService.getUserById(userId)));
		resultMap.put("success", true);
		return resultMap;
	}
	

	/**
	 * 修改用户密码
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyPassword")
	@RequiresPermissions(value="修改密码")
	public Map<String, Object> modifyPassword(String newPassword,HttpSession session){
		Map<String, Object> resultMap=new HashMap<>();
		User currentUser=(User) session.getAttribute("currentUser");
		User user=userService.getUserById(currentUser.getUserId());
		user.setPassword(newPassword);
		userService.saveUser(user);
		logService.log(new Log(Log.UPDATE_ACTION, "修改了密码"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 安全退出
	 * @param session
	 * @return
	 */
	@GetMapping("/logout")
	@RequiresPermissions(value="修改密码")
	public String logout(HttpSession session){
		logService.log(new Log(Log.LOGOUT_ACTION, "用户退出登录"));
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
	
}
