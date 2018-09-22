package com.helmet.service;

import java.util.List;

import com.helmet.entity.Menu;

/**
 * 权限菜单接口
 * 
 * @author Helmet
 * 2018年6月28日
 */
public interface MenuService {
	
	/**
	 * 通过父节点以及角色id获取菜单
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuByRoleId(int parentId,int roleId);
	
	/**
	 * 根据父节点查询菜单
	 * @param parentId
	 * @return
	 */
	public List<Menu> getMenuByParentId(int parentId);
	
	/**
	 * 根据menuId查询菜单权限
	 * @param menuId
	 * @return
	 */
	public Menu getMenuByMenuId(int menuId);
}
