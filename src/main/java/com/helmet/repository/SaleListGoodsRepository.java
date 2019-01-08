package com.helmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.SaleListGoods;

/**
 * 销售单商品Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface SaleListGoodsRepository extends JpaRepository<SaleListGoods, Integer> ,JpaSpecificationExecutor<SaleListGoods> {
	
	/**
	 * 根据销售单Id获取销售单的所以商品信息
	 * @param saleListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_sale_list_goods WHERE sale_list_id =?1",nativeQuery=true)
	public List<SaleListGoods> getGoodsListBySaleListId(Integer saleListId);
	
	/**
	 * 根据销售单id 删除该销售单中的商品
	 * @param returnListId
	 */
	@Modifying
	@Query(value="delete from t_sale_list_goods where sale_list_id=?1",nativeQuery=true)
	public void deleteBySaleListId(Integer saleListId);
	
	/**
	 * 根据商品Id获取该商品的销售总数
	 * @param goodsId
	 * @return
	 */
	@Query(value="select SUM(num) as saleTotalNum from t_sale_list_goods where goods_id=?1",nativeQuery=true)
	public Integer getSaleTotalNum(Integer goodsId);
}
