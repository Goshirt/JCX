package com.helmet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helmet.entity.GoodsUnit;
import com.helmet.repository.GoodsUnitRespository;
import com.helmet.service.GoodsUnitService;

@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService{

	@Resource
	private GoodsUnitRespository goodsUnitRespository;
	
	
	@Override
	public List<GoodsUnit> getUnits() {
		return goodsUnitRespository.findAll();
	}


	@Override
	public void save(GoodsUnit goodsUnit) {
		goodsUnitRespository.save(goodsUnit);
	}


	@Override
	public void delete(Integer unitId) {
		goodsUnitRespository.delete(unitId);
	}

}
