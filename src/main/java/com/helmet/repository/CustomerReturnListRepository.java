package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.CustomerReturnList;

/**
 * 客户退货单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface CustomerReturnListRepository extends JpaRepository<CustomerReturnList , Integer> ,JpaSpecificationExecutor<CustomerReturnList> {
	
	/**
	 * 获取当天最大的客户退货单号
	 * @return
	 */
	@Query(value="SELECT MAX(customer_return_number) FROM t_customer_return_list WHERE TO_DAYS(customer_return_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxCustomerReturnNumber();
	
	/**
	 * 更新客户退货单的付款状态为已付
	 * @param puchaseListId
	 */
	@Modifying
	@Query(value="update t_customer_return_list set state=1 where customer_return_list_id=?1",nativeQuery=true)
	public void updateState(Integer customerReturnListId);
}
