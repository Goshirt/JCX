package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 货物类型实体
 * 
 * @author Helmet
 * 2018年8月21日
 */
@Entity
@Table(name="t_goodsType")
public class GoodsType {
	
	@Id
	@GeneratedValue
	private Integer goodsTypeId; //编号
	
	@Column(length=50)
	private String name;  //商品类型
	
	private int parentId;  //父亲节点
	
	private int state;  //状态标志    0: 父亲节点    1: 叶子节点
	
	@Column(length=50)
	private String icon;  //图标

	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "GoodsType [goodsTypeId=" + goodsTypeId + ", name=" + name + ", state=" + state + ", icon=" + icon + "]";
	}
	
	
}
