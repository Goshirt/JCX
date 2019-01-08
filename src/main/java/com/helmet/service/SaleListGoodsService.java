package com.helmet.service;

import java.util.List;

import com.helmet.entity.SaleListGoods;

/**
 * 
 * 销售单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface SaleListGoodsService {
	
	
	/**
	 * 根据销售单Id获取销售单的所有商品
	 * @param saleListId
	 * @sale
	 */
	public List<SaleListGoods> getSaleListGoodsBySaleId(Integer saleListId);
	
	
	/**
	 * 根据goodsId获取该商品的销售总数
	 * @param goodsId
	 * @return
	 */
	public Integer getSaleTotalNum(Integer goodsId);
	
	
	
	
	
}
