package com.helmet.service;

import com.helmet.entity.UserRole;

/**
 * 用户角色关联Service
 * 
 * @author Helmet
 * 2018年5月30日
 */
public interface UserRoleService {
	
	/**
	 * 根据userId删除关联关系
	 * @param deleteId
	 */
	public void deleteByUserId(Integer userId);
	
	/**
	 * 保存用户角色关联关系
	 * @param userRole
	 */
	public void save(UserRole userRole);
	
	
	/**
	 * 根据roleId删除关联关系
	 * @param deleteId
	 */
	public void deleteByRoleId(Integer roleId);
}
