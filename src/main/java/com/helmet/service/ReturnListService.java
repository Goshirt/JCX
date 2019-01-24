package com.helmet.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

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
	
	/**
	 * 根据进货单信息分页查询获取进货单列表,如果条件为空，则查找出所有的退货单
	 * @param returnList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param propertis
	 * @return
	 */
	public List<ReturnList> list(ReturnList returnList,Direction direction,String...propertis);
	
	/**
	 * 根据进货单信息查询总数
	 * @param returnList
	 * @return
	 */
	public Long count(ReturnList returnList);
	
	/**
	 * 根据退货单Id
	 * @param returnListId
	 */
	public void delete(Integer returnListId);
	
	/**
	 * 更新退货单的付款状态为已付
	 * @param returnListId
	 */
	public void updateState(Integer returnListId);
}
