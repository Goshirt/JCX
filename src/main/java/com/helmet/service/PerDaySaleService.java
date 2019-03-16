package com.helmet.service;

import com.helmet.entity.CountPerDaySale;

import java.util.List;

/**
 * @author Helmet
 * @site www.helmet.xin
 * @create 2019-03-06 21:30
 */
public interface PerDaySaleService {

    /**
     * 获取b在指定时间范围内的每天销售统计数据
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<CountPerDaySale> getCountPerDaySaleByDate(String beginDate, String endDate);
}
