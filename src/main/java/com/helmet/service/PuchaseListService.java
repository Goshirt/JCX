package com.helmet.service;

import java.util.List;

import com.helmet.entity.PuchaseList;
import com.helmet.entity.PuchaseListGoods;

/**
 * 
 * 进货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface PuchaseListService {
	
	/**
	 * 获取当天最大的进货单号
	 * @return
	 */
	public String getTodayMaxPuchaseNumber();
	
	/**
	 * 保存进货单以及进货单的所有商品集，并修改仓库中商品的库存、平均进货价
	 * @param puchaseList
	 * @param puchaseListGoodsList
	 */
	public void save(PuchaseList puchaseList,List<PuchaseListGoods> puchaseListGoodsList);
}
