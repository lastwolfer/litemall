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


    @Autowired
    GoodsService goodsService;

    /**
     * 商品列表,分页和排序
     */
    @RequestMapping("admin/goods/list")
    public BaseReqVo goodsList(QuerryGoodsList querryGoodsList) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
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
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
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
     *
     * @param
     * @return
     */
    @RequestMapping("admin/goods/create")
    public BaseReqVo createGoods(@RequestBody GoodsCreateBean goodsCreateBean) throws JsonProcessingException {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.createGoods(goodsCreateBean);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 根据id 查询商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping("admin/goods/detail")
    public BaseReqVo selectGoodsById(Integer id) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        GoodsCreateBean goodsCreateBean = goodsService.selectGoodsById(id);
        baseReqVo.setErrno(0);
        baseReqVo.setData(goodsCreateBean);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 商品信息更新
     *
     * @param goodsCreateBean
     * @return
     */
    @RequestMapping("admin/goods/update")
    public BaseReqVo updateGoods(@RequestBody GoodsCreateBean goodsCreateBean) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.updateGoods(goodsCreateBean);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 删除商品
     *
     * @param map
     * @return
     */
    @RequestMapping("admin/goods/delete")
    public BaseReqVo deleteGoods(@RequestBody Map map) {
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.deleteGoods(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("admin/comment/list")
    public BaseReqVo commentList(QuerryCommentList querryCommentList){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        List<Comment> commentList = goodsService.commentList(querryCommentList);
        long total = new PageInfo<>(commentList).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", commentList);
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
