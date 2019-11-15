package com.pandax.litemall.service;

import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.CategoryList;
import com.pandax.litemall.bean.Goods;
import com.pandax.litemall.bean.QuerryGoodsList;

import java.util.List;

public interface GoodsService {
    List<Goods> goodsList(QuerryGoodsList querryGoodsList);

    List<CategoryList> QuerryCat();

    List QuerryBrand();
    public int countGoods();

}
