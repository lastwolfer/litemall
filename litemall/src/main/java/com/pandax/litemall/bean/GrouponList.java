package com.pandax.litemall.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GrouponList {
    private BigDecimal groupon_price;
    private Goods goods;
    private Integer grouponMember;
}
