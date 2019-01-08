package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.LossStatement;

/**
 * 报损单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface LossStatementRepository extends JpaRepository<LossStatement, Integer> ,JpaSpecificationExecutor<LossStatement> {
	
	/**
	 * 获取当天最大的报损单号
	 * @return
	 */
	@Query(value="SELECT MAX(loss_number) FROM t_loss_statement WHERE TO_DAYS(create_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxLossNumber();
}
