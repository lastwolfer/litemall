package com.pandax.reponseJson;

import com.pandax.litemall.bean.*;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDetail {

    List<SpecificationList> specificationLists;

    List<Groupon> groupon;

    List<Issue> issue;

    Long userHasCollect;

    Comments comment;

    List<GoodsAttribute> attribute;

    Brand brand;

    List<GoodsProduct> productList;

    Goods info;



}
