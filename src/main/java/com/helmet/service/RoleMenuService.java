package com.helmet.service;

import java.util.List;

import com.helmet.entity.RoleMenu;

public interface RoleMenuService {
	
	/**
	 * 根据roleId删除呢角色菜单关联关系
	 * @param roleId
	 */
	public void deleteByRoleId(Integer roleId);
	
	
	/**
	 * 根据roleId查找该角色拥有的菜单权限集合
	 * @param roleId
	 * @return
	 */
	public List<RoleMenu> getRoleMenuByRoleId(Integer roleId);
	
	/**
	 * 保存一个menuRole实体
	 * @param roleMenu
	 */
	public void saveRoleMenu(RoleMenu roleMenu);
}
