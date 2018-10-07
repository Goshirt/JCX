package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.PuchaseListGoods;

/**
 * 进货单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface PuchaseListGoodsRepository extends JpaRepository<PuchaseListGoods, Integer> ,JpaSpecificationExecutor<PuchaseListGoods> {
	
	/**
	 * 根据进货单Id获取进货单的所以商品信息
	 * @param puchaseListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_puchase_list_goods WHERE puchase_list_id =?1",nativeQuery=true)
	public List<PuchaseListGoods> getGoodsListByPuchaseListId(Integer puchaseListId);
}
