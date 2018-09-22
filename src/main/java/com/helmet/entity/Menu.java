package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单实体
 * 
 * @author Helmet
 * 2018年5月29日
 */
@Entity
@Table(name="t_menu")
public class Menu {
	
	@Id
	@GeneratedValue
	private Integer menuId;
	
	@Column(length=100)
	private String menuName;//菜单名
	
	@Column(length=100)
	private String url;//菜单对应的链接
	
	@Column(length=100)
	private String icon;//菜单的图标名
	
	private Integer parentId;//父亲节点
	
	private Integer state;//状态标志，1：父亲节点       0：叶子节点

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
