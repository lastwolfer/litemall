package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class QuerryGoodsList {
    private Integer page;
    private Integer limit;
    private String goodsSn;
    private String name;
    private String sort;
    private String order;
}
