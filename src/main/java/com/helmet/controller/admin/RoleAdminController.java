package com.helmet.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.helmet.entity.Log;
import com.helmet.entity.Menu;
import com.helmet.entity.Role;
import com.helmet.entity.RoleMenu;
import com.helmet.service.LogService;
import com.helmet.service.MenuService;
import com.helmet.service.RoleMenuService;
import com.helmet.service.RoleService;
import com.helmet.service.UserRoleService;
import com.helmet.util.StringUtil;

/**
 * 用户角色controller层
 * 
 * @author Helmet
 * 2018年8月12日
 */
@RequestMapping("/admin/role")
@Controller
public class RoleAdminController {
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleMenuService roleMenuService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 获取所有的角色集
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/roleList")
	@RequiresPermissions(value={"用户管理","角色管理"},logical=Logical.OR)
	public Map<String, Object> getRoleList() {
		Map<String, Object> resultMap=new HashMap<>();
		List<Role> roleList=roleService.getRoleList();
		resultMap.put("rows", roleList);
		logService.log(new Log(Log.SELECT_ACTION, "用户在用户管理页面获取点击了授予角色，获取了角色集"));
		return resultMap;
	}

	/**
	 * 角色管理模块，可以通过条件获取分页的角色集
	 * @param role
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/roleListByName")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> getRoleList(Role role,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer pageSize){
		Map<String, Object> resultMap=new HashMap<>();
		List<Role> roles=roleService.getRoleList(role, page, pageSize, Direction.ASC, "id");
		Long count=roleService.count(role);
		resultMap.put("rows", roles);
		resultMap.put("total", count);
		logService.log(new Log(Log.SELECT_ACTION, "获取了角色集"));
		return resultMap;
	}
	
	/**
	 * 保存一个角色实体
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> save(Role role){
		Map<String, Object> resultMap=new HashMap<>();
		if (role!=null) {
			if (role.getRoleId()==null) {
				Role haveRole=roleService.getRoleByName(role.getRoleName());
				if (haveRole!=null) {
					resultMap.put("success", false);
					resultMap.put("errorInfo", "该角色名已经存在");
					return resultMap;
				}
			}
		}
		
		//记录用户操作的日志
		if (role!=null) {
			if (role.getRoleId()==null) {
				logService.log(new Log(Log.ADD_ACTION, "添加一个角色"+role.toString()));
			}else {
				logService.log(new Log(Log.UPDATE_ACTION, "更新角色信息"+role.toString()));
			}
		}
		roleService.saveRole(role);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 删除一个角色实体
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> delete(Integer roleId){
		Map<String, Object> resultMap=new HashMap<>();
		roleMenuService.deleteByRoleId(roleId);
		userRoleService.deleteByRoleId(roleId);
		logService.log(new Log(Log.DELETE_ACTION, "删除了一个角色:"+roleService.getRoleByRoleId(roleId).toString()));
		roleService.deleteRole(roleId);
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 获取权限菜单树，并且把该角色已经拥有的权限菜单选中
	 * @param parentId
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadMenuTree")
	@RequiresPermissions(value="角色管理")
	public String getCheckedMenuTree(Integer parentId,Integer roleId){
		//跟据roleId获取到该角色已经拥有的权限菜单
		List<RoleMenu> roleMenus=roleMenuService.getRoleMenuByRoleId(roleId);
		//把该权限菜单的menuId作为一个集合，方便下一步的判断
		List<Integer> menuIds=new ArrayList<>();
		for (RoleMenu roleMenu : roleMenus) {
			menuIds.add(roleMenu.getMenu().getMenuId());
		}
		logService.log(new Log(Log.SELECT_ACTION, "用户点击了授予权限，获取了权限菜单树"));
		return getAllMenutree(parentId, menuIds).toString();
	}
	
	/**
	 * 根据父节点获取一层的菜单
	 * @param parentId
	 * @param menuIds
	 * @return
	 */
	public JsonArray getOneFloorMenu(Integer parentId,List<Integer> menuIds){
		JsonArray jsonArray=new JsonArray();
		List<Menu> oneFloorMenus=menuService.getMenuByParentId(parentId);
		for (Menu menu : oneFloorMenus) {
			JsonObject oneMenu=new JsonObject();
			//封装easyui树的数据格式，key是固定写法
			oneMenu.addProperty("id", menu.getMenuId());
			oneMenu.addProperty("text", menu.getMenuName());
			//如果状态是1，代表该节点是可关闭的，下边还有子节点
			if (menu.getState()==1) {
				oneMenu.addProperty("state", "closed");
			}else {
				oneMenu.addProperty("state", "open");
			}
			//如果menuIds中存在该节点Id,就选中勾选上
			if (menuIds.contains(menu.getMenuId())) {
				oneMenu.addProperty("checked", true);
			}
			oneMenu.addProperty("iconCls", menu.getIcon());
			jsonArray.add(oneMenu);
		}
		return jsonArray;
	}
	
	/**
	 * 递归生成整棵菜单权限树，并且把指定角色已经拥有的菜单权限勾选上
	 * @param parentId
	 * @param menuIds
	 * @return
	 */
	public JsonArray getAllMenutree(Integer parentId,List<Integer> menuIds){
		JsonArray jsonArray=this.getOneFloorMenu(parentId, menuIds);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			//如果该节点的state属性值为open,证明底下没有孩子节点，进行下一步循环
			if ("open".equals(jsonObject.get("state").getAsString())) {
				continue;
			}else {
				jsonObject.add("children", getOneFloorMenu(jsonObject.get("id").getAsInt(), menuIds));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 保存给指点roleId角色授予的菜单权限
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveMenus")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> saveMenus(String menuIds,Integer roleId){
		Map<String, Object> resultMap=new HashMap<>();
		//先删除之前该角色拥有的菜单权限
		roleMenuService.deleteByRoleId(roleId);
		if (StringUtil.isNotEmpty(menuIds)) {
			String[] menuIdStr=menuIds.split(",");
			for (int i = 0; i < menuIdStr.length; i++) {
				RoleMenu roleMenu=new RoleMenu();
				roleMenu.setRole(roleService.getRoleByRoleId(roleId));
				roleMenu.setMenu(menuService.getMenuByMenuId(Integer.parseInt(menuIdStr[i])));
				roleMenuService.saveRoleMenu(roleMenu);
			}
		}
		logService.log(new Log(Log.UPDATE_ACTION, "更新了角色拥有的权限"));
		resultMap.put("success", true);
		return resultMap;
	}
}
