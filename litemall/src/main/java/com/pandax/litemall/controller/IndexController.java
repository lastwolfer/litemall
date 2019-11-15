package com.pandax.litemall.controller;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.HomePageInfo;
import com.pandax.litemall.service.GoodsService;
import com.pandax.litemall.service.OrderService;
import com.pandax.litemall.service.ProductService;
import com.pandax.litemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @RequestMapping("admin/dashboard")
    @ResponseBody
    public BaseReqVo showHomepage(){
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

}
