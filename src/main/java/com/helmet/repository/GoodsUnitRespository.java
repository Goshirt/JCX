package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.helmet.entity.GoodsUnit;

/**
 * 商品单位Respository
 * 
 * @author Helmet
 * 2018年8月28日
 */
public interface GoodsUnitRespository extends JpaRepository<GoodsUnit, Integer>,JpaSpecificationExecutor<GoodsUnit>{

}
