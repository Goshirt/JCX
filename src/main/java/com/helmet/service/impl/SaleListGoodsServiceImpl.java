package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.helmet.entity.SaleListGoods;
import com.helmet.repository.SaleListGoodsRepository;
import com.helmet.service.SaleListGoodsService;

/**
 * 进货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService{
	
	
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	


	@Override
	public List<SaleListGoods> getSaleListGoodsBySaleId(Integer saleListId) {
		return saleListGoodsRepository.getGoodsListBySaleListId(saleListId);
	}




	@Override
	public Integer getSaleTotalNum(Integer goodsId) {
		//如果售货单中没有该商品的销售记录，查询记录为null,赋值0
		return saleListGoodsRepository.getSaleTotalNum(goodsId) == null ? 0 : saleListGoodsRepository.getSaleTotalNum(goodsId);
	}
	


}
