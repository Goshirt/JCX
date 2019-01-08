package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.helmet.entity.LossStatement;
import com.helmet.entity.LossStatementGoods;

/**
 * 
 * 报损单Service
 * @author Helmet
 * 2018年9月8日
 */
public interface LossStatementService {
	
	/**
	 * 获取当天最大的报损单号
	 * @return
	 */
	public String getTodayMaxLossNumber();
	
	/**
	 * 保存报损单以及报损单的所有商品集，并修改仓库中商品的库存、平均报损价
	 * @param lossStatement
	 * @param lossStatementGoodsList
	 */
	public void save(LossStatement lossStatement,List<LossStatementGoods> lossStatementGoodsList);
	
	/**
	 * 根据报损单信息分页查询获取报损单列表
	 * @param lossStatement
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<LossStatement> list(LossStatement lossStatement,Direction direction,String...propertis);
	
	/**
	 * 根据报损单信息查询总数
	 * @param lossStatement
	 * @return
	 */
	public Long count(LossStatement lossStatement);
	
	/**
	 * 根据报损单Id删除报损单
	 * @param lossStatementId
	 */
	public void delete(Integer lossStatementId);
}
