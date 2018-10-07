package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.helmet.entity.Role;
import com.helmet.repository.RoleRepository;
import com.helmet.service.RoleService;
import com.helmet.util.StringUtil;

/**
 * 角色service实现类
 * 
 * @author Helmet
 * 2018年5月30日
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private RoleRepository roleRepository;

	@Override
	public List<Role> getRolesByUserId(Integer userId) {
		return roleRepository.getRolesByUserId(userId);
	}

	@Override
	public Role getRoleByRoleId(Integer roleId) {
		return roleRepository.findOne(roleId);
	}

	@Override
	public List<Role> getRoleList() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> getRoleList(Role role, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		//把分页信息封装到Pageable(sprint Data 工具类)类，默认第一页是0,所以需要把传递的page-1
		Pageable pageable=new PageRequest(page-1, pageSize);
		//每一页的Role集
		Page<Role> rolePage=roleRepository.findAll(new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (role!=null) {
					if (StringUtil.isNotEmpty(role.getRoleName())) {
						//模糊查询角色名
						predicate.getExpressions().add(cb.like(root.get("roleName"), "%"+role.getRoleName()+"%"));
					}
					//去除超级管理员角色
					predicate.getExpressions().add(cb.notEqual(root.get("roleId"), 1));
				}
				return predicate;
			}
		}, pageable);
		//返回每一页Role集的List形式
		return rolePage.getContent();
	}

	@Override
	public Long count(Role role) {
		Long count=roleRepository.count(new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if (role!=null) {
					if (StringUtil.isNotEmpty(role.getRoleName())) {
						predicate.getExpressions().add(cb.like(root.get("roleName"), "%"+role.getRoleName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("roleId"), 1));
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void saveRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void deleteRole(Integer roleId) {
		roleRepository.delete(roleId);
	}

	@Override
	public Role getRoleByName(String roleName) {
		return roleRepository.getRoleByName(roleName);
	}


}
