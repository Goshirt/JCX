package com.helmet.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色和菜单的映射关系
 * 
 * @author Helmet
 * 2018年5月29日
 */
@Entity
@Table(name="t_roleMenu")
public class RoleMenu {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role;
	
	
	@ManyToOne
	@JoinColumn(name="menuId")
	private Menu menu;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Menu getMenu() {
		return menu;
	}


	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	
}
