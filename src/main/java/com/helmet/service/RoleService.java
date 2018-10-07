package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.Role;

/**
 * 角色service接口
 * 
 * @author Helmet
 * 2018年5月30日
 */
public interface RoleService {
	
	/**
	 * 通过userId查找该登录用户所拥有的权限
	 * @param userId
	 * @return
	 */
	public List<Role> getRolesByUserId(Integer userId);
	
	/**
	 * 根据roleId获取角色实体
	 * @param roleId
	 * @return
	 */
	public Role getRoleByRoleId(Integer roleId);
	
	/**
	 * 获取所有的角色集合
	 * @return
	 */
	public List<Role> getRoleList();
	
	/**
	 * 根据条件获取角色集，并且分页和排序
	 * @param role
	 * @param page
	 * @param pageSize
	 * @param direction 排序
	 * @param properties
	 * @return
	 */
	public List<Role> getRoleList(Role role , Integer page , Integer pageSize,Direction direction,String...properties );
	
	/**
	 * 根据条件获取角色总数
	 * @param role
	 * @return
	 */
	public Long count(Role role);
	
	/**
	 * 保存一个角色实体
	 * @param role
	 */
	public void saveRole(Role role);
	
	
	/**
	 * 删除一个角色实体
	 * @param roleId
	 */
	public void deleteRole(Integer	roleId);
	
	/**
	 * 通过名字查找是否已经存在该角色名
	 * @param roleName
	 * @return
	 */
	public Role getRoleByName(String roleName);
	
	
}
