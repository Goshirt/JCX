package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.helmet.entity.ReturnListGoods;

/**
 * 退货单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListGoodsRepository extends JpaRepository<ReturnListGoods, Integer> ,JpaSpecificationExecutor<ReturnListGoods> {

}
