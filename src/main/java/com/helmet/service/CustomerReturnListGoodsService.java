package com.helmet.service;

import java.util.List;

import com.helmet.entity.CustomerReturnListGoods;

/**
 * 
 * 客户退货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface CustomerReturnListGoodsService {
	
	
	/**
	 * 根据客户退货单Id获取客户退货单的所有商品
	 * @param customerReturnListId
	 * @sale
	 */
	public List<CustomerReturnListGoods> getCustomerReturnListGoodsByCustomerReturnListId(Integer customerReturnListId);
	
	
	/**
	 * 根据goodsId获取该商品的退货总数
	 * @param goodsId
	 * @return
	 */
	public Integer getReturnTotalNum(Integer goodsId);
	
	
}
