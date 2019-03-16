package com.helmet.repository;

import com.helmet.entity.CountPerDaySale;
import com.helmet.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 每天销售统计Repository
 * @author Helmet
 * @site www.helmet.xin
 * @create 2019-03-03 17:26
 */
public interface PerDaySaleRepository extends JpaRepository<CountPerDaySale, Integer> , JpaSpecificationExecutor<Goods> {
    /**
     * 获取b在指定时间范围内的每天销售统计数据
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value = "SELECT SUM(tg.purchasing_price*tslg.num) AS costTotalMoney,SUM(tslg.num*tslg.price) AS saleTotalMoney,tsl.sale_date FROM t_sale_list_goods tslg LEFT JOIN t_sale_list tsl ON tslg.sale_list_id = tsl.sale_list_id LEFT JOIN t_goods tg ON tslg.goods_id = tg.goods_id WHERE tsl.sale_date BETWEEN ?1 AND ?2 GROUP BY tsl.sale_date ",nativeQuery = true)
    public List<CountPerDaySale> getCountPerDaySaleByDate(String beginDate, String endDate);
}
