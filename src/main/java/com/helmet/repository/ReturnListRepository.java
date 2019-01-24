package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.ReturnList;

/**
 * 退货单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListRepository extends JpaRepository<ReturnList, Integer> ,JpaSpecificationExecutor<ReturnList> {
	
	/**
	 * 获取当天最大的退货单号
	 * @return
	 */
	@Query(value="SELECT MAX(puchase_number) FROM t_return_list WHERE TO_DAYS(puchase_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxReturnNumber();
	
	/**
	 * 更新退货单的付款状态为已付
	 * @param returnListId
	 */
	@Modifying
	@Query(value="update t_return_list set state=1 where return_list_id=?1",nativeQuery=true)
	public void updateState(Integer returnListId);
}
