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

    /**
     * 所以评论
     * @param querryCommentList
     * @return
     */
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

    /**
     * 评论回复
     * @param commentReply
     * @return
     */
    @RequestMapping("admin/order/reply")
    public BaseReqVo reply(@RequestBody CommentReply commentReply){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int i = goodsService.reply(commentReply);
        if (i == 0){
            baseReqVo.setErrno(622);
            baseReqVo.setErrmsg("订单商品已回复！");
        }else {
            baseReqVo .setErrno(0);
            baseReqVo.setErrmsg("成功");
        }
        return baseReqVo;
    }

    /**
     * 删除评论
     * @param comment
     * @return
     */
    @RequestMapping("/admin/comment/delete")
    public BaseReqVo deleteComment(@RequestBody Comment comment){
        BaseReqVo<Override> baseReqVo = new BaseReqVo<>();
        int i = goodsService.deleteComment(comment);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    //微信小程序------------------------------------------

    /**
     * 商品总数
     * @return
     */
    @RequestMapping("wx/goods/count")
    public BaseReqVo countGoods(){
        BaseReqVo baseReqVo = new BaseReqVo();
        int i = goodsService.countGoods();
        baseReqVo.setErrno(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsCount",i);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /**
     * 商品列表
     */
    @RequestMapping("wx/goods/list")
    public BaseReqVo wxGoodsList(QuerryGoodsList querryGoodsList){
       return goodsList(querryGoodsList);
    }

    @RequestMapping("wx/comment/list")
    public BaseReqVo wxCommentList(QuerryCommentList querryCommentList){
        return commentList(querryCommentList);
    }

}
