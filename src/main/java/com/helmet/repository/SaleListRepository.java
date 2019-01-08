package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.SaleList;

/**
 * 销售单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface SaleListRepository extends JpaRepository<SaleList, Integer> ,JpaSpecificationExecutor<SaleList> {
	
	/**
	 * 获取当天最大的销售单号
	 * @return
	 */
	@Query(value="SELECT MAX(sale_number) FROM t_sale_list WHERE TO_DAYS(sale_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxSaleNumber();
}
