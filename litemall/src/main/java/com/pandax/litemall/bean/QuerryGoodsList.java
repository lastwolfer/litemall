package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class QuerryGoodsList {
    private Integer page;
    private Integer limit;
    private Integer size;
    private String goodsSn;
    private String name;
    private Integer categoryId;
    private Integer brandId;
    private String keyword;
    private String sort;
    private String order;
}
