package com.helmet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品单位实体
 * 
 * @author Helmet
 * 2018年8月28日
 */
@Entity
@Table(name="t_unit")
public class GoodsUnit {
	
	@Id
	@GeneratedValue
	private Integer unitId;
	
	@Column(length=5)
	private String unitName;

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	
	public GoodsUnit() {
		super();
	}
	public GoodsUnit(Integer unitId, String unitName) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
	}

	@Override
	public String toString() {
		return "GoodsUnit [unitId=" + unitId + ", unitName=" + unitName + "]";
	}
	
	

}
