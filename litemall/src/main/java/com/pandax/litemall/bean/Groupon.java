package com.pandax.litemall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class Groupon {
    private Integer id;

    private Integer orderId;

    private Integer grouponId;

    private Integer rulesId;

    private Integer userId;

    private Integer creatorUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String shareUrl;

    private Boolean payed;

    private Boolean deleted;

    //接收团购活动的商品id参数
    private Integer goodsId;

}
