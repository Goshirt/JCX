package com.helmet.service;

import java.util.List;

import com.helmet.entity.ReturnList;
import com.helmet.entity.ReturnListGoods;

/**
 * 
 * 退货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListService {
	
	/**
	 * 获取当天最大的退货单号
	 * @return
	 */
	public String getTodayMaxReturnNumber();
	
	/**
	 * 保存退货单以及退货单的所有商品集，并修改仓库中商品的库存、平均进货价
	 * @param returnList
	 * @param returnListGoodsList
	 */
	public void save(ReturnList returnList,List<ReturnListGoods> returnListGoodsList);
}
