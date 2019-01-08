package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.helmet.entity.CustomerReturnListGoods;
import com.helmet.repository.CustomerReturnListGoodsRepository;
import com.helmet.service.CustomerReturnListGoodsService;

/**
 * 进货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService{
	
	
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;


	@Override
	public List<CustomerReturnListGoods> getCustomerReturnListGoodsByCustomerReturnListId(
			Integer customerReturnListId) {
		return customerReturnListGoodsRepository.getGoodsListByCustomerReturnListId(customerReturnListId);
	}


	@Override
	public Integer getReturnTotalNum(Integer goodsId) {
		//如果退货单中没有该商品的退货记录，查询记录为null，则默认为 0
		return customerReturnListGoodsRepository.getReturnTotalNum(goodsId) == null ? 0 : customerReturnListGoodsRepository.getReturnTotalNum(goodsId);
	}
	


}
