package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

@Data
public class StatGoodsData {
    String[] columns = {"day","orders","products","amount"};
    private List<StatGoodsDb> rows;
}
