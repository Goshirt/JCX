package com.helmet.service;


/**
 * 
 * 进货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface PuchaseListGoodsService {
	
	/**
	 * 获取当天最大的进货单号
	 * @return
	 */
	public String getTodayMaxPuchaseNumber();
}
