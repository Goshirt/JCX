package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.Role;

/**
 * 角色repository接口
 * 
 * @author Helmet
 * 2018年5月30日
 */
public interface RoleRepository extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{
	
	/**
	 * 通过userId查找该登录用户所拥有的权限
	 * @param userId
	 * @return
	 */
	@Query(value="select r.* from t_user u,t_role r,t_user_role ur where u.`user_id`=ur.`user_id` and r.`role_id`=ur.`role_id` and u.`user_id`=?1",nativeQuery=true)
	public List<Role> getRolesByUserId(Integer userId);
	
	
	/**
	 * 通过名字查找是否已经存在该角色名
	 * @param roleName
	 * @return
	 */
	@Query(value="select * from t_role where role_name=?1",nativeQuery=true)
	public Role getRoleByName(String roleName);
}
