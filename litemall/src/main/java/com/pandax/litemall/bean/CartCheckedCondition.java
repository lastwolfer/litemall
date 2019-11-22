package com.pandax.litemall.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartCheckedCondition {

    /**
     * grouponPrice : 0
     * grouponRulesId : 0
     * checkedAddress : {"id":0}
     * actualPrice : 66
     * orderTotalPrice : 66
     * couponPrice : 0
     * availableCouponLength : 0
     * couponId : 0
     * freightPrice : 43
     * checkedGoodsList : [{"productId":750,"addTime":"2019-11-20 06:28:44","goodsId":1181047,"goodsSn":"777777","updateTime":"2019-11-20 10:05:39","userId":6,"specifications":["12"],"number":1,"picUrl":"http://192.168.2.100:8081/wx/storage/fetch/9ouab1wa0a7ll196hfe9.jpg","deleted":false,"price":23,"checked":true,"id":1190,"goodsName":"杨豆豆"}]
     * goodsTotalPrice : 23
     * addressId : 0
     */
    private Integer grouponPrice=0;
    private Integer grouponRulesId=0;
    private Address checkedAddress;
    private Double actualPrice;
    private Double orderTotalPrice;
    private Integer couponPrice = 0;
    private Integer availableCouponLength;
    private Integer couponId =0;
    private Integer freightPrice = 0;
    private List<Cart> checkedGoodsList;
    private Double goodsTotalPrice;
    private int addressId=0;
}
