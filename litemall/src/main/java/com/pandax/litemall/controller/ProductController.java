package com.pandax.litemall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.pandax.litemall.bean.*;
import com.pandax.litemall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
    @Autowired
    GoodsService goodsService;

    /**
     * 商品列表,分页和排序
     */
    @RequestMapping("admin/goods/list")
    public BaseReqVo goodsList(QuerryGoodsList querryGoodsList) {
        List<Goods> goodsList = goodsService.goodsList(querryGoodsList);
        long total = new PageInfo<>(goodsList).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", goodsList);

        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    /**
     * 商品上架信息填写
     *
     * @return
     */
    @RequestMapping("admin/goods/catAndBrand")
    public BaseReqVo catAndBrand() {
        List<CategoryList> categoryList = goodsService.QuerryCat();
        List brandList = goodsService.QuerryBrand();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryList);
        map.put("brandList", brandList);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 商品上架
     * @param goodsCreateBean
     * @return
     */
    @RequestMapping("admin/goods/create")
    public BaseReqVo createGoods(@RequestBody String string) throws JsonProcessingException {
        System.out.println(string);
        ObjectMapper objectMapper = new ObjectMapper();
        GoodsCreateBean goodsCreateBean = objectMapper.readValue(string, GoodsCreateBean.class);
        System.out.println(goodsCreateBean);
        return baseReqVo;
    }

}
