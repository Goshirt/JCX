package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.Goods;

/**
 * 商品Repository
 * 
 * @author Helmet
 * 2018年8月23日
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer> ,JpaSpecificationExecutor<Goods> {

	/**
	 * 通过goodsTypeId统计该商品类型下有多少商品
	 * @param goodsTypeId
	 * @return
	 */
	@Query(value="select count(goods_id) from t_goods where goods_type_id=?1",nativeQuery=true)
	public Long countGoodsByGoodsTypeId(Integer goodsTypeId);
	
	
	/**
	 * 获取最大的商品码
	 * @return
	 */
	@Query(value="SELECT MAX(CODE) FROM t_goods",nativeQuery=true)
	public String getMaxCode();
	
	/**
	 * 获取库存报警的商品
	 * @return
	 */
	@Query(value="SELECT * FROM t_goods g ,t_goods_type t WHERE  g.inventory_quantity < g.min_num AND g.goods_type_id = t.goods_type_id ORDER BY t.name",nativeQuery=true)
	public List<Goods> getAlarmGoods();
}
