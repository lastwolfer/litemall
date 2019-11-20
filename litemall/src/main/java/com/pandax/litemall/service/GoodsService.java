package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;
import com.pandax.reponseJson.GoodsDetail;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    List<Goods> goodsList(QuerryGoodsList querryGoodsList);

    List<CategoryList> QuerryCat();

    List QuerryBrand();

    int countGoods();

    int createGoods(GoodsCreateBean goodsCreateBean);

    GoodsCreateBean selectGoodsById(Integer id);

    int updateGoods(GoodsCreateBean goodsCreateBean);

    int deleteGoods(Map map);

    List<Comment> commentList(QuerryCommentList querryCommentList);

    int reply(CommentReply commentReply);

    int deleteComment(Comment comment);

    List<Goods> selectNewGoods();

    List<Category> selectCategoryL1();

    List<Goods> selectHotGoods();

    List selectCategoryAndGoods();

    Map goodsCount();

    Map selectCategoryByGoodsId(Integer id);

    GoodsDetail selectGoodsDetailByGoodsId(Integer id);

    Map selectAllBrand(Integer page, Integer size);

    Map selectBrandById(Integer id);

    Map selectGoodsByCategoryId(Integer categoryId, Integer page, Integer size);

    Map selectGoodsRelatedByGoodsId(Integer id);

    Map selectBrandByBrandId(Integer brandId, Integer page, Integer size);

    Map selectGoodsByKeyword(String keyword, String sort, String order, Integer page, Integer size, Integer categoryId);
}
