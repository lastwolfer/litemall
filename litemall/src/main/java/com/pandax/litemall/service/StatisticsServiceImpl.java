package com.pandax.litemall.service;

import com.pandax.litemall.bean.StatGoodsDb;
import com.pandax.litemall.bean.StatOrderDb;
import com.pandax.litemall.bean.StatUserDb;
import com.pandax.litemall.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public List<StatUserDb> getUserStat() {
        return statisticsMapper.getUserByDay();
    }

    @Override
    public List<StatOrderDb> getOrderStat() {
        List<StatOrderDb> orderStat = statisticsMapper.getOrderStat();

        for (StatOrderDb stat : orderStat) {

            stat.setPcr(stat.getAmount()/stat.getCustomers());
        }
        return orderStat;
    }

    @Override
    public List<StatGoodsDb> getOrderGoodsStat() {
        return statisticsMapper.getOrderGoodsStat();
    }
}
