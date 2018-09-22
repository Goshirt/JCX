package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.entity.RoleMenu;
import com.helmet.repository.RoleMenuRepository;
import com.helmet.service.RoleMenuService;


/**
 * 角色菜单关联关系接口实现
 * @Transactional 这是事务注解
 * @author Helmet
 * 2018年8月16日
 */
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService{
	
	@Resource
	private RoleMenuRepository roleMenuRepository;

	@Override
	public void deleteByRoleId(Integer roleId) {
		roleMenuRepository.deleteByRoleId(roleId);
	}

	@Override
	public List<RoleMenu> getRoleMenuByRoleId(Integer roleId) {
		return roleMenuRepository.getRoleMenuByRoleId(roleId);
	}

	@Override
	public void saveRoleMenu(RoleMenu roleMenu) {
		roleMenuRepository.save(roleMenu);
	}


}
