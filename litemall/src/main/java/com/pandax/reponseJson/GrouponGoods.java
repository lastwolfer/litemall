package com.pandax.reponseJson;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GrouponGoods {
    private BigDecimal groupon_price;
    private ResponseGrouponGoods goods;
    private Long groupon_member;
}
