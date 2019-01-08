package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.helmet.entity.LossStatementGoods;
import com.helmet.repository.LossStatementGoodsRepository;
import com.helmet.service.LossStatementGoodsService;

/**
 * 报损单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("lossStatementGoodsService")
public class LossStatementGoodsServiceImpl implements LossStatementGoodsService{
	
	
	@Resource
	private LossStatementGoodsRepository lossStatementGoodsRepository;

	@Override
	public Integer getLossTotalNum(Integer goodsId) {
		//如果售货单中没有该商品的销售记录，查询记录为null,赋值0
		return lossStatementGoodsRepository.getLossTotalNum(goodsId) == null ? 0 : lossStatementGoodsRepository.getLossTotalNum(goodsId);
	}

	@Override
	public List<LossStatementGoods> getLossStatementGoodsByLossStatementId(Integer lossStatementId) {
		return lossStatementGoodsRepository.getGoodsListByLossStatementId(lossStatementId);
	}
	


}
