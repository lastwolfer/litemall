package com.pandax.litemall.service;


import com.pandax.litemall.bean.StatGoodsDb;
import com.pandax.litemall.bean.StatOrderDb;
import com.pandax.litemall.bean.StatUserDb;

import java.util.List;

public interface StatisticsService {

    List<StatUserDb> getUserStat();


    List<StatOrderDb> getOrderStat();

    List<StatGoodsDb> getOrderGoodsStat();
}
