package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helmet.entity.ReturnListGoods;
import com.helmet.repository.ReturnListGoodsRepository;
import com.helmet.service.ReturnListGoodsService;

@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService{

	@Resource
	private ReturnListGoodsRepository returnListGoodsRepository;

	@Override
	public List<ReturnListGoods> getReturnListGoodsByReturnId(Integer returnListId) {
		return returnListGoodsRepository.getGoodsListByPuchaseListId(returnListId);
	}

}
