package com.helmet.service;

import java.util.List;

import com.helmet.entity.PuchaseListGoods;

/**
 * 
 * 进货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface PuchaseListGoodsService {
	
	
	/**
	 * 根据进货单Id获取进货单的所以商品信息
	 * @param puchaseListId
	 * @return
	 */
	public List<PuchaseListGoods> getGoodsListByPuchaseListId(Integer puchaseListId);
}
