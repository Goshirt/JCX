package com.helmet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helmet.entity.UserRole;
import com.helmet.repository.UserRoleRepository;
import com.helmet.service.UserRoleService;


/**
 * 用户角色关联实现类
 * 
 * @author Helmet
 * 2018年8月9日
 */
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Resource
	private UserRoleRepository userRoleRepository;
	
	
	@Override
	public void deleteByUserId(Integer userId) {
		userRoleRepository.deleteByUserId(userId);
	}


	@Override
	public void save(UserRole userRole) {
		userRoleRepository.save(userRole);
		
	}


	@Override
	public void deleteByRoleId(Integer roleId) {
		userRoleRepository.deleteByRoleId(roleId);
	}

}
