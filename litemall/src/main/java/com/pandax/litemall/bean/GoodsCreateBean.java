package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

/**
 * 接收上架商品信息的bean
 */
@Data
public class GoodsCreateBean {
    private Goods goods;
    private List<GoodsSpecification> specifications;
    private List<GoodsProduct> products;
    private List<GoodsAttribute> attributes;
}
