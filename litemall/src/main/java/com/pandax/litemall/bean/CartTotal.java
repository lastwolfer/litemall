package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class CartTotal {

    /**
     * goodsCount : 2
     * checkedGoodsCount : 2
     * goodsAmount : 123
     * checkedGoodsAmount : 123
     */
    private int goodsCount;
    private int checkedGoodsCount;
    private Double goodsAmount;
    private Double checkedGoodsAmount;


}
