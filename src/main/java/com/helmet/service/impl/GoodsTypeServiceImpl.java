package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helmet.entity.GoodsType;
import com.helmet.repository.GoodsTypeRepository;
import com.helmet.service.GoodTypeService;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodTypeService{

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	
	@Override
	public List<GoodsType> geGoodsTypesByParentId(Integer parentId) {
		return goodsTypeRepository.geGoodsTypesByParentId(parentId);
	}


	@Override
	public GoodsType getGoodsTypeById(Integer goodsTypeId) {
		return goodsTypeRepository.getOne(goodsTypeId);
	}


	@Override
	public void save(GoodsType goodsType) {
		goodsTypeRepository.save(goodsType);
	}


	@Override
	public void delete(Integer goodsTypeId) {
		goodsTypeRepository.delete(goodsTypeId);
	}

}
