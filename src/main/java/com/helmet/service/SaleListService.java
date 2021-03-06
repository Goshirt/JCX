package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.SaleList;
import com.helmet.entity.SaleListGoods;

/**
 * 
 * 销售单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface SaleListService {
	
	/**
	 * 获取当天最大的销售单号
	 * @return
	 */
	public String getTodayMaxSaleNumber();
	
	/**
	 * 保存销售单以及销售单的所有商品集，并修改仓库中商品的库存、平均销售价
	 * @param saleList
	 * @param saleListGoodsList
	 */
	public void save(SaleList saleList,List<SaleListGoods> saleListGoodsList);
	
	/**
	 * 根据销售单信息分页查询获取销售单列表
	 * @param saleList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<SaleList> list(SaleList saleList,Direction direction,String...propertis);
	
	/**
	 * 根据销售单信息查询总数
	 * @param saleList
	 * @return
	 */
	public Long count(SaleList saleList);
	
	/**
	 * 根据销售单Id删除销售单
	 * @param saleListId
	 */
	public void delete(Integer saleListId);
	
	/**
	 * 更新销售单的付款状态为已付
	 * @param saleListId
	 */
	public void updateState(Integer saleListId);

	/**
	 * 获取在指定时间范围内的每天销售统计数据
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Object> getCountPerDaySaleByDate(String beginDate, String endDate);

	/**
	 * 获取在指定时间范围内的每月销售统计数据
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Object> getCountPerMonthSaleByDate(String beginDate, String endDate);
}
