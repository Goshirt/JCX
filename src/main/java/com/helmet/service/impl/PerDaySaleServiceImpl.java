package com.helmet.service.impl;

import com.helmet.entity.CountPerDaySale;
import com.helmet.repository.PerDaySaleRepository;
import com.helmet.service.PerDaySaleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Helmet
 * @site www.helmet.xin
 * @create 2019-03-06 22:08
 */
@Service("perDaySaleService")
public class PerDaySaleServiceImpl implements PerDaySaleService {

    @Resource
    private PerDaySaleRepository perDaySaleRepository;

    @Override
    public List<CountPerDaySale> getCountPerDaySaleByDate(String beginDate, String endDate) {
        return perDaySaleRepository.getCountPerDaySaleByDate(beginDate,endDate);
    }

}
