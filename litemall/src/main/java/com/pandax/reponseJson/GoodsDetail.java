package com.pandax.reponseJson;

import com.pandax.litemall.bean.*;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDetail {

    List<SpecificationList> specificationList;

    List<GrouponRules> groupon;

    List<Issue> issue;

    String shareImage;

    Long userHasCollect;

    Comments comment;

    List<GoodsAttribute> attribute;

    Brand brand;

    List<GoodsProduct> productList;

    Goods info;



}
