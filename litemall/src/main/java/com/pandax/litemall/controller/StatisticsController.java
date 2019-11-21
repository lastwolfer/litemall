package com.pandax.litemall.controller;


import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {


    @Autowired
    StatisticsService statisticsService;

    @RequestMapping("admin/stat/user")
    public BaseReqVo getUserStat(){
        BaseReqVo baseReqVo = new BaseReqVo();
        StatUserData statUserData = new StatUserData();
        List<StatUserDb> stat = statisticsService.getUserStat();
        if (stat != null){
            statUserData.setRows(stat);
            baseReqVo.setData(statUserData);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        return null;
    }


    @RequestMapping("admin/stat/order")
    public BaseReqVo getOrderStat(){
        BaseReqVo baseReqVo = new BaseReqVo();
        StatOrderData statOrderData = new StatOrderData();
        List<StatOrderDb> stat = statisticsService.getOrderStat();
        if (stat != null){
            statOrderData.setRows(stat);
            baseReqVo.setData(statOrderData);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        return null;
    }

    @RequestMapping("admin/stat/goods")
    public BaseReqVo getOrderGoodsStat(){
        BaseReqVo baseReqVo = new BaseReqVo();
        StatGoodsData statGoodsData = new StatGoodsData();
        List<StatGoodsDb> stat = statisticsService.getOrderGoodsStat();
        if (stat != null){
            statGoodsData.setRows(stat);
            baseReqVo.setData(statGoodsData);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            return baseReqVo;
        }
        return null;
    }
}
