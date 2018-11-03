package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.ReturnListGoods;

/**
 * 退货单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface ReturnListGoodsRepository extends JpaRepository<ReturnListGoods, Integer> ,JpaSpecificationExecutor<ReturnListGoods> {

	@Query(value="select * from t_return_list_goods where return_list_id = ?1",nativeQuery=true)
	public List<ReturnListGoods> getGoodsListByPuchaseListId(Integer returnListId);
	
	
	/**
	 * 根据进货单id 删除该进货单中的商品
	 * @param returnListId
	 */
	@Query(value="delete from t_return_list_goods where return_list_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByReturnListId(Integer returnListId);
}
