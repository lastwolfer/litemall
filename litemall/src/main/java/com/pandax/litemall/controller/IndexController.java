package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.*;
import com.pandax.litemall.util.BaseRespVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
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

    /**
     * 微信首页
     *
     * @return
     */
    @RequestMapping("wx/home/index")
    public BaseReqVo indexHome() {
        HashMap<String, Object> dataMap = new HashMap<>();
        List<Goods> newGoodsList = goodsService.selectNewGoods();
        dataMap.put("newGoodsList", newGoodsList);
        List<Coupon> couponList = promotionService.selectCoupon();
        dataMap.put("couponList", couponList);
        List<Category> channel = goodsService.selectCategoryL1();
        dataMap.put("channel", channel);
        List<GrouponList> grouponList = promotionService.selectGrouponList();
        dataMap.put("grouponList", grouponList);
        List<Banner> banner = indexService.selectBanner();
        dataMap.put("banner", banner);
        List<Brand> brandList = indexService.selectBrand();
        dataMap.put("brandList", brandList);
        List<Goods> hotGoodsList = goodsService.selectHotGoods();
        dataMap.put("hotGoodsList", hotGoodsList);
        List<Topic> topicList = indexService.selectTopic();
        dataMap.put("topicList",topicList);
        List floorGoodsList = goodsService.selectCategoryAndGoods();
        dataMap.put("floorGoodsList", floorGoodsList);
        return BaseReqVo.ok(dataMap);
    }

    /**
     * 微信商品分类页
     */
    @RequestMapping("wx/catalog/index")
    public BaseReqVo indexCatalog(Integer id) {
        Category currentCategory = new Category();
        if (id == null) {//如果为空 则为/wx/catalog/index
            currentCategory = indexService.selectCategoryOne();
        } else {//如果不为空 则为/wx/catalog/current
            currentCategory = indexService.selectCategoryL1ById(id);
        }
        List<Category> categoryList = goodsService.selectCategoryL1();
        List<Category> currentSubCategory = indexService.selectCategoryByPid(currentCategory.getId());
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("currentCategory", currentCategory);
        dataMap.put("currentSubCategory", currentSubCategory);
        dataMap.put("categoryList", categoryList);
        return BaseReqVo.ok(dataMap);
    }

    @RequestMapping("wx/catalog/current")
    public BaseReqVo currentCatalogById(Integer id) {
        return indexCatalog(id);
    }


    /**
     * 用户订单状态
     *
     * @return
     */
    @RequestMapping("wx/user/index")
    public BaseReqVo userIndex() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            return BaseReqVo.fail(500,"请登录");
        }
        Integer userId = user.getId();
        Short status1 = 401;//未评论
        List<Order> orderList1 = orderService.selectOrderByUserIdAndStatus(userId, status1);
        Short status2 = 101;//未付款
        List<Order> orderList2 = orderService.selectOrderByUserIdAndStatus(userId, status2);
        Short status3 = 201;//未发货
        List<Order> orderList3 = orderService.selectOrderByUserIdAndStatus(userId, status3);
        Short status4 = 301;//未收货
        List<Order> orderList4 = orderService.selectOrderByUserIdAndStatus(userId, status4);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uncomment", orderList1.size());
        map.put("unrecv", orderList4.size());
        map.put("unpaid", orderList2.size());
        map.put("unship", orderList3.size());
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", map);
        return BaseReqVo.ok(data);
    }
}
