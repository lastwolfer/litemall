package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.*;
import com.pandax.litemall.util.BaseRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    IndexService indexService;

    @RequestMapping("admin/dashboard")
    @ResponseBody
    public BaseReqVo showHomepage() {
        BaseReqVo baseReqVo = new BaseReqVo();
        HomePageInfo data = new HomePageInfo();
        int countGoods = goodsService.countGoods();
        int countUsers = userService.countUsers();
        int countProducts = productService.countProducts();
        int countOrders = orderService.countOrders();
        data.setGoodTotal(countGoods);
        data.setUserTotal(countUsers);
        data.setProductTotal(countProducts);
        data.setOrderTotal(countOrders);
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("wx/home/index")
    @ResponseBody
    public BaseReqVo indexHome() {
        HashMap<String, Object> dataMap = new HashMap<>();
        List<Goods> newGoodsList = goodsService.selectNewGoods();
        dataMap.put("newGoodsList", newGoodsList);
        List<Coupon> couponList = promotionService.selectCoupon();
        dataMap.put("couponList", couponList);
        List<Category> channel = goodsService.selectCategoryL1();
        dataMap.put("channel",channel);
        List<GrouponList> grouponList = promotionService.selectGrouponList();
        dataMap.put("grouponList",grouponList);
        List<Banner> banner = indexService.selectBanner();
        dataMap.put("banner",banner);
        List<Brand> brandList = indexService.selectBrand();
        dataMap.put("brandList",brandList);
        List<Goods> hotGoodsList = goodsService.selectHotGoods();
        dataMap.put("hotGoodsList",hotGoodsList);
        List floorGoodsList = goodsService.selectCategoryAndGoods();
        dataMap.put("floorGoodsList",floorGoodsList);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(dataMap);
        return baseReqVo;
    }

}
