package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

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
	
	/**
	 * 根据进货单信息分页查询获取进货单列表
	 * @param puchaseList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<PuchaseList> list(PuchaseList puchaseList,Integer page,Integer pageSize,Direction direction,String...propertis);
	
	/**
	 * 根据进货单信息查询总数
	 * @param puchaseList
	 * @return
	 */
	public Long count(PuchaseList puchaseList);
	
	/**
	 * 根据进货单Id删除进货单
	 * @param puchaseListId
	 */
	public void delete(Integer puchaseListId);
}
