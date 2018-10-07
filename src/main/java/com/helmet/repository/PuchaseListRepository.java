package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.PuchaseList;

/**
 * 进货单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface PuchaseListRepository extends JpaRepository<PuchaseList, Integer> ,JpaSpecificationExecutor<PuchaseList> {
	
	/**
	 * 获取当天最大的进货单号
	 * @return
	 */
	@Query(value="SELECT MAX(puchase_number) FROM t_puchase_list WHERE TO_DAYS(puchase_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxPuchaseNumber();
}
