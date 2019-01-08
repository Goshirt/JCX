package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.CustomerReturnListGoods;


/**
 * 客户退货单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface CustomerReturnListGoodsRepository extends JpaRepository<CustomerReturnListGoods, Integer> ,JpaSpecificationExecutor<CustomerReturnListGoods> {
	
	/**
	 * 根据客户退货单Id获取客户退货单的所以商品信息
	 * @param customerReturnListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_customer_return_list_goods WHERE customer_return_list_id =?1",nativeQuery=true)
	public List<CustomerReturnListGoods> getGoodsListByCustomerReturnListId(Integer customerReturnListId);
	
	/**
	 * 根据客户退货单id 删除该客户退货单中的商品
	 * @param customerReturnListId
	 */
	@Modifying
	@Query(value="delete from t_customer_return_list_goods where customer_return_id=?1",nativeQuery=true)
	public void deleteByCustomerReturnListId(Integer customerReturnListId);
	
	
	/**
	 * 根据商品Id获取该商品的退货总数
	 * @param goodsId
	 * @return
	 */
	@Query(value="select SUM(num) as returnTotalNum from t_customer_return_list_goods where goods_id=?1",nativeQuery=true)
	public Integer getReturnTotalNum(Integer goodsId);
}
