package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.helmet.entity.OverflowStatementGoods;
import com.helmet.repository.OverflowStatementGoodsRepository;
import com.helmet.service.OverflowStatementGoodsService;

/**
 * 报损单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("overflowStatementGoodsService")
public class OverflowStatementGoodsServiceImpl implements OverflowStatementGoodsService{
	
	
	@Resource
	private OverflowStatementGoodsRepository overflowStatementGoodsRepository;

	@Override
	public Integer getOverflowTotalNum(Integer goodsId) {
		//如果售货单中没有该商品的销售记录，查询记录为null,赋值0
		return overflowStatementGoodsRepository.getOverflowTotalNum(goodsId) == null ? 0 : overflowStatementGoodsRepository.getOverflowTotalNum(goodsId);
	}

	@Override
	public List<OverflowStatementGoods> getOverflowStatementGoodsByOverflowStatementId(Integer overflowStatementId) {
		return overflowStatementGoodsRepository.getGoodsListByOverflowStatementId(overflowStatementId);
	}
	


}
