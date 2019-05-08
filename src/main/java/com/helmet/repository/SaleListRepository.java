package com.helmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.helmet.entity.SaleList;

import java.util.List;

/**
 * 销售单Repository
 * 
 * @author Helmet
 * 2018年9月8日
 */
public interface SaleListRepository extends JpaRepository<SaleList, Integer> ,JpaSpecificationExecutor<SaleList> {
	
	/**
	 * 获取当天最大的销售单号
	 * @return
	 */
	@Query(value="SELECT MAX(sale_number) FROM t_sale_list WHERE TO_DAYS(sale_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxSaleNumber();
	
	/**
	 * 更新销售单的付款状态为已付
	 * @param saleListId
	 */
	@Modifying
	@Query(value="update t_sale_list set state=1 where sale_list_id=?1",nativeQuery=true)
	public void updateState(Integer saleListId);

	/**
	 * 获取b在指定时间范围内的每天销售统计数据
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "SELECT " +
			"SUM(tslg.num*tg.purchasing_price) AS costTotalMoney," +
			"SUM(tslg.num*tslg.price) AS saleTotalMoney,tsl.sale_date AS saleDate"+
			" FROM t_sale_list_goods tslg LEFT JOIN t_sale_list tsl ON tslg.sale_list_id = tsl.sale_list_id " +
			"LEFT JOIN t_goods tg ON tslg.goods_id = tg.goods_id "+
			"WHERE tsl.sale_date BETWEEN ?1 AND ?2 " +
			"GROUP BY tsl.sale_date ",
			nativeQuery = true)
	public List<Object> getCountPerDaySaleByDate(String beginDate, String endDate);

	@Query(value = "SELECT " +
			"SUM(tslg.`num`*tslg.`price`) AS saleTotalMoney," +
			"SUM(tslg.`num`*tg.`purchasing_price`) AS costTotalMoney," +
			"DATE_FORMAT(tsl.`sale_date`,'%Y-%m')AS saleMonth"+
			" FROM t_sale_list tsl LEFT JOIN t_sale_list_goods tslg ON tsl.`sale_list_id`=tslg.`sale_list_id` " +
			"LEFT JOIN t_goods tg ON tslg.`goods_id`=tg.`goods_id` "+
			"WHERE DATE_FORMAT(tsl.`sale_date`,'%Y-%m') BETWEEN ?1 AND ?2 " +
			"GROUP BY DATE_FORMAT(tsl.`sale_date`,'%Y-%m')",
			nativeQuery = true)
	public List<Object> getCountPerMonthSaleByDate(String beginDate,String endDate);
}
