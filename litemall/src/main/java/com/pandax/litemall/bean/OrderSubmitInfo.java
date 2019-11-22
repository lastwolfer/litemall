package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class OrderSubmitInfo {

   private Integer addressId;
   private Integer cartId;
   private Integer couponId;
   private Integer grouponLinkId;
   private Integer grouponRulesId;
   private String message;

}
