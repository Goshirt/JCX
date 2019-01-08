package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.OverflowStatement;

/**
 * 报溢单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface OverflowStatementRepository extends JpaRepository<OverflowStatement, Integer> ,JpaSpecificationExecutor<OverflowStatement> {
	
	/**
	 * 获取当天最大的报溢单号
	 * @return
	 */
	@Query(value="SELECT MAX(overflow_number) FROM t_overflow_statement WHERE TO_DAYS(create_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxOverflowNumber();
}
