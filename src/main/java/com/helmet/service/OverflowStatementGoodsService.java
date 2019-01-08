package com.helmet.service;

import java.util.List;

import com.helmet.entity.OverflowStatementGoods;

/**
 * 
 * 报溢单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface OverflowStatementGoodsService {
	
	
	/**
	 * 根据报溢单Id获取报溢单的所有商品
	 * @param overflowStatementId
	 * @sale
	 */
	public List<OverflowStatementGoods> getOverflowStatementGoodsByOverflowStatementId(Integer overflowStatementId);
	
	
	/**
	 * 根据goodsId获取该商品的报溢总数
	 * @param goodsId
	 * @return
	 */
	public Integer getOverflowTotalNum(Integer goodsId);
	
	
	
	
	
}
