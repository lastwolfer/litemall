package com.pandax.reponseJson;

import com.pandax.litemall.bean.Brand;
import com.pandax.litemall.bean.GoodsAttribute;
import com.pandax.litemall.bean.Groupon;
import com.pandax.litemall.bean.Issue;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDetail {

    List<SpecificationList> specificationLists;

    List<Groupon> groupon;

    List<Issue> issue;

    Integer userHasCollect;

    Comments comment;

    GoodsAttribute attribute;

    Brand brand;



}
