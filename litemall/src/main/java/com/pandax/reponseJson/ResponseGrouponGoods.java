package com.pandax.reponseJson;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseGrouponGoods {
    private Integer id;
    private String name;
    private String brief;
    private String picUrl;
    private BigDecimal counterPrice;
    private BigDecimal retailPrice;

}
