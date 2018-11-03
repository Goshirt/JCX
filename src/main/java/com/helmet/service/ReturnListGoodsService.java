package com.helmet.service;

import java.util.List;

import com.helmet.entity.ReturnListGoods;

/**
 * 
 * 退货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListGoodsService {
	
	
	/**
	 * 根据退货单Id获取退货单的所有商品
	 * @param returnListId
	 * @return
	 */
	public List<ReturnListGoods> getReturnListGoodsByReturnId(Integer returnListId);
	
	
	
	
	
}
