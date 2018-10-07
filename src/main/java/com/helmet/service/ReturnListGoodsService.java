package com.helmet.service;


/**
 * 
 * 退货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListGoodsService {
	
	/**
	 * 获取当天最大的退货单号
	 * @return
	 */
	public String getTodayMaxReturnNumber();
	
	
}
