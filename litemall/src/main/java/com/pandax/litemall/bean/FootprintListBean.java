package com.pandax.litemall.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FootprintListBean {
    /**
     * brief : good
     * picUrl : http://192.168.2.100:8081/wx/storage/fetch/q9fj4feku1lnokdk1opj.jpg
     * addTime : 2019-11-21 07:14:29
     * goodsId : 1181024
     * name : 手枪
     * id : 4278
     * retailPrice : 3121
     */
    private String brief;
    private String picUrl;
    private Date addTime;
    private int goodsId;
    private String name;
    private int id;
    private BigDecimal retailPrice;

}

