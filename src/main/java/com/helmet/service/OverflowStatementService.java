package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.OverflowStatement;
import com.helmet.entity.OverflowStatementGoods;

/**
 * 
 * 报溢单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface OverflowStatementService {
	
	/**
	 * 获取当天最大的报溢单号
	 * @return
	 */
	public String getTodayMaxOverflowNumber();
	
	/**
	 * 保存报溢单以及报溢单的所有商品集，并修改仓库中商品的库存、平均报溢价
	 * @param overflowStatement
	 * @param overflowStatementGoodsList
	 */
	public void save(OverflowStatement overflowStatement,List<OverflowStatementGoods> overflowStatementGoodsList);
	
	/**
	 * 根据报溢单信息分页查询获取报溢单列表
	 * @param overflowStatement
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<OverflowStatement> list(OverflowStatement overflowStatement,Direction direction,String...propertis);
	
	/**
	 * 根据报溢单信息查询总数
	 * @param overflowStatement
	 * @return
	 */
	public Long count(OverflowStatement overflowStatement);
	
	/**
	 * 根据报溢单Id删除报溢单
	 * @param overflowStatementId
	 */
	public void delete(Integer overflowStatementId);
}
