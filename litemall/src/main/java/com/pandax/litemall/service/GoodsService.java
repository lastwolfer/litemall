package com.pandax.litemall.service;

import com.pandax.litemall.bean.*;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    List<Goods> goodsList(QuerryGoodsList querryGoodsList);

    List<CategoryList> QuerryCat();

    List QuerryBrand();
    public int countGoods();

    int createGoods(GoodsCreateBean goodsCreateBean);

    GoodsCreateBean selectGoodsById(Integer id);

    int updateGoods(GoodsCreateBean goodsCreateBean);

    int deleteGoods(Map map);

    List<Comment> commentList(QuerryCommentList querryCommentList);


    int reply(CommentReply commentReply);

    int deleteComment(Comment comment);

    Map goodsCount();

    Map selectCategoryByGoodsId(Integer id);

    Map selectGoodsDetailByGoodsId(Integer id);
}
