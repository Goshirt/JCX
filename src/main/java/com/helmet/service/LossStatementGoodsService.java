package com.helmet.service;

import java.util.List;

import com.helmet.entity.LossStatementGoods;

/**
 * 
 * 报损单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface LossStatementGoodsService {
	
	
	/**
	 * 根据报损单Id获取报损单的所有商品
	 * @param lossStatementId
	 * @sale
	 */
	public List<LossStatementGoods> getLossStatementGoodsByLossStatementId(Integer lossStatementId);
	
	
	/**
	 * 根据goodsId获取该商品的报损总数
	 * @param goodsId
	 * @return
	 */
	public Integer getLossTotalNum(Integer goodsId);
	
	
	
	
	
}
