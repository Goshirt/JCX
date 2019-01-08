package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.CustomerReturnList;
import com.helmet.entity.CustomerReturnListGoods;

/**
 * 
 * 客户退货单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface CustomerReturnListService {
	
	/**
	 * 获取当天最大的客户退货单号
	 * @return
	 */
	public String getTodayMaxCustomerReturnNumber();
	
	/**
	 * 保存客户退货单以及客户退货单的所有商品集，并修改仓库中商品的库存、平均客户退货价
	 * @param customerReturnList
	 * @param customerReturnListGoodsList
	 */
	public void save(CustomerReturnList customerReturnList,List<CustomerReturnListGoods> customerReturnListGoodsList);
	
	/**
	 * 根据客户退货单信息分页查询获取客户退货单列表
	 * @param customerReturnList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<CustomerReturnList> list(CustomerReturnList customerReturnList,Integer page,Integer pageSize,Direction direction,String...propertis);
	
	/**
	 * 根据客户退货单信息查询总数
	 * @param customerReturnList
	 * @return
	 */
	public Long count(CustomerReturnList customerReturnList);
	
	/**
	 * 根据客户退货单Id删除客户退货单
	 * @param customerReturnListId
	 */
	public void delete(Integer customerReturnListId);
}
