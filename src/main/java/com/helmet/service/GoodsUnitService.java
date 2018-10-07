package com.helmet.service;

import java.util.List;

import com.helmet.entity.GoodsUnit;

/**
 * 商品单位Service
 * 
 * @author Helmet
 * 2018年8月28日
 */
public interface GoodsUnitService {
	
	/**
	 * 获取所有的单位
	 * @return
	 */
	public List<GoodsUnit> getUnits();
	
	/**
	 * 保存一个单位实体
	 * @param goodsUnit
	 */
	public void save(GoodsUnit goodsUnit);
	
	/**
	 * 删除一个单位实体 
	 * @param unitId
	 */
	public void delete(Integer unitId);
}
