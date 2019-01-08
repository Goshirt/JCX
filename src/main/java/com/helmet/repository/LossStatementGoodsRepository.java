package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.LossStatementGoods;

/**
 * 报损单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface LossStatementGoodsRepository extends JpaRepository<LossStatementGoods, Integer> ,JpaSpecificationExecutor<LossStatementGoods> {
	
	/**
	 * 根据报损单Id获取报损单的所有商品信息
	 * @param saleListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_loss_statement_goods WHERE loss_statement_id =?1",nativeQuery=true)
	public List<LossStatementGoods> getGoodsListByLossStatementId(Integer lossStatementId);
	
	/**
	 * 根据报损单id 删除该报损单中的商品
	 * @param returnListId
	 */
	@Modifying
	@Query(value="delete from t_loss_statement_goods where loss_statement_id=?1",nativeQuery=true)
	public void deleteByLossStatementId(Integer lossStatement);
	
	/**
	 * 根据商品Id获取该商品的报损总数
	 * @param goodsId
	 * @return
	 */
	@Query(value="select SUM(num) as lossTotalNum from t_loss_statement_goods where goods_id=?1",nativeQuery=true)
	public Integer getLossTotalNum(Integer goodsId);
}
