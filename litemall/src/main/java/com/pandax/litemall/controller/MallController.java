package com.pandax.litemall.controller;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.OrderMapper;
import com.pandax.litemall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MallController {
    @Autowired
    MallService mallService;
    @Autowired
    OrderMapper orderMapper;

    @RequestMapping("admin/region/list")
    public BaseReqVo region() {
        Region[] region = mallService.region();
        BaseReqVo<Region[]> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(region);
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/brand/list")
    public BaseReqVo brandList(Integer page, Integer limit, String sort, String order,Integer id,String name) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.brand(page,limit,sort,order,id,name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/update")
    public BaseReqVo brandUpdate(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Brand brand = mallService.brandUpdate(newBrand);
        baseReqVo.setData(brand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/delete")
    public BaseReqVo brandDelete(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        mallService.brandDelete(newBrand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/brand/create")
    public BaseReqVo brandCreate(@RequestBody Brand newBrand) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Brand brand = mallService.brandCreate(newBrand);
        baseReqVo.setData(brand);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/category/list")
    public BaseReqVo categoryList() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Category[] categories = mallService.categoryList();
        baseReqVo.setData(categories);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/category/l1")
    public BaseReqVo categoryL1() {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Category[] categories = mallService.categoryL1();
        baseReqVo.setData(categories);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/category/create")
    public BaseReqVo categoryCreate(@RequestBody Category category) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Category categories = mallService.categoryCreate(category);
        baseReqVo.setData(categories);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/category/update")
    public BaseReqVo categoryUpdate(@RequestBody Category category) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        mallService.categoryUpdate(category);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/category/delete")
    public BaseReqVo categoryDelete(@RequestBody Category category) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        mallService.categoryDelete(category);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/order/list")
    public BaseReqVo orderList(Integer page, Integer limit,Integer orderStatusArray, String sort, String order,Integer userId,String orderSn) {
        System.out.println("orderStatusArray="+orderStatusArray);
        System.out.println("userId="+userId);
        System.out.println("orderSn="+orderSn);
        System.out.println("".equals(orderSn));
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.orderList(page,limit,orderStatusArray,sort,order,userId,orderSn);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/order/detail")
    public BaseReqVo orderDetail(Integer id) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.orderDetail(id);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/issue/list")
    public BaseReqVo issueList(Integer page,Integer limit,String question,String sort,String order) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.issueList(page,limit,question,sort,order);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/keyword/list")
    public BaseReqVo keywordList(Integer page,Integer limit,String keyword,String url,String sort,String order) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = mallService.keywordList(page,limit,keyword,url,sort,order);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/keyword/update")
    public BaseReqVo keywordUpdate(@RequestBody Keyword keyword) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Keyword keyword1 = mallService.keywordUpdate(keyword);
        baseReqVo.setData(keyword1);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/keyword/delete")
    public BaseReqVo keywordDelete(@RequestBody Keyword keyword) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        mallService.keywordDelete(keyword);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("admin/keyword/create")
    public BaseReqVo keywordCreate(@RequestBody Keyword keyword) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Keyword keyword1=mallService.keywordCreate(keyword);
        baseReqVo.setData(keyword1);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/order/ship")
    public BaseReqVo orderShip(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        String shipChannel = (String) map.get("shipChannel");
        String shipSn = (String) map.get("shipSn");
        int updateStatus = orderMapper.updateShipInfo(orderId,shipChannel,shipSn);
        if (updateStatus != -1) {
            return BaseReqVo.ok();
        }else{
            return BaseReqVo.fail();
        }
    }
}
