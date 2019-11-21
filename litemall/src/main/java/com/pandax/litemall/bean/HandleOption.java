package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class HandleOption {

    //是否可取消订单
    private Boolean cancel;
    //是否可删除订单
    private Boolean delete;
    //是否可付款
    private Boolean pay;
    //是否可评论
    private Boolean comment;
    //是否可确认收货
    private Boolean confirm;
    //是否可申请退款
    private Boolean refund;
    //是否可再次购买
    private Boolean rebuy;
}
