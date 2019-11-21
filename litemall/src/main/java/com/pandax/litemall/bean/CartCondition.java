package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;
@Data
public class CartCondition {


    /**
     * productIds : [748]
     * isChecked : 0
     */
    private List<Integer> productIds;
    private int isChecked;

}
