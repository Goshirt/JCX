package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helmet.entity.Menu;
import com.helmet.repository.MenuRepository;
import com.helmet.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService{

	@Resource
	private MenuRepository menuRepository;
	
	@Override
	public List<Menu> getMenuByRoleId(int parentId, int roleId) {
		return menuRepository.getMenuByRoleId(parentId, roleId);
	}

	@Override
	public List<Menu> getMenuByParentId(int parentId) {
		return menuRepository.getMenuByParentId(parentId);
	}

	@Override
	public Menu getMenuByMenuId(int menuId) {
		return menuRepository.findOne(menuId);
	}

}
