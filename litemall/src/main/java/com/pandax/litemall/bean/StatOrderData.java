package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;

@Data
public class StatOrderData {
    String[] columns = {"day","orders","customers","amount","pcr"};
    private List<StatOrderDb> rows;

}
