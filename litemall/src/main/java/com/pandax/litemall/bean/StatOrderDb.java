package com.pandax.litemall.bean;


import lombok.Data;


@Data
public class StatOrderDb {
    private double amount;
    private int orders;
    private int customers;
    private String day;
    private double pcr;
}
