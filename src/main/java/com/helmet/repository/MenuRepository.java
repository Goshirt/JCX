package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.Menu;


public interface MenuRepository extends JpaRepository<Menu, Integer>{

	/**
	 * 根据父节点和角色id查询菜单
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	@Query(value="select * from t_menu where parent_id=?1 and menu_id in (select menu_id from t_role_menu where role_id=?2)",nativeQuery=true)
	public List<Menu> getMenuByRoleId(int parentId,int roleId);
	
	/**
	 * 根据roleId获取所有的权限菜单
	 * @param roleId
	 * @return
	 */
	@Query(value="SELECT m.* FROM t_role_menu rm,t_role r,t_menu m WHERE rm.`role_id`=r.`role_id` AND rm.`menu_id`=m.`menu_id` AND r.`role_id`=?1",nativeQuery=true)
	public List<Menu> getMenuByRoleId(int roleId);
	
	/**
	 * 根据父节点查询菜单
	 * @param parentId
	 * @return
	 */
	@Query(value="select * from t_menu where parent_id=?1",nativeQuery=true)
	public List<Menu> getMenuByParentId(int parentId);
}
