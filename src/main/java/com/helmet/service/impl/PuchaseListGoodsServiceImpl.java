package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.helmet.entity.PuchaseListGoods;
import com.helmet.repository.PuchaseListGoodsRepository;
import com.helmet.service.PuchaseListGoodsService;

/**
 * 进货单Service实现
 * 
 * @author Helmet
 * 2018年9月8日
 */
@Service("puchaseListGoodsService")
public class PuchaseListGoodsServiceImpl implements PuchaseListGoodsService{
	
	
	@Resource
	private PuchaseListGoodsRepository puchaseListGoodsRepository;

	
	@Override
	public List<PuchaseListGoods> getGoodsListByPuchaseListId(Integer puchaseListId) {
		return puchaseListGoodsRepository.getGoodsListByPuchaseListId(puchaseListId);
	}
	


}
