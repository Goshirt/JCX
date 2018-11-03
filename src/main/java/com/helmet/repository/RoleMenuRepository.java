package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.RoleMenu;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer>,JpaSpecificationExecutor<RoleMenu>{
	
	/**
	 * 根据roleId删除角色菜单关联关系
	 * @Modifying 使用jpa自定义的update 或者 delete操作时，需要添加该注解
	 * @param roleId
	 */
	@Query(value="delete from t_role_menu where role_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByRoleId(Integer roleId);
	
	
	/**
	 * 根据roleId查找该角色拥有的菜单权限集合
	 * @param roleId
	 * @return
	 */
	@Query(value="select * from t_role_menu where role_id=?1",nativeQuery=true)
	public List<RoleMenu> getRoleMenuByRoleId(Integer roleId);
}
