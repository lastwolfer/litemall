package com.pandax.reponseJson;

import com.pandax.litemall.bean.GoodsSpecification;
import lombok.Data;

import java.util.List;

@Data
public class SpecificationList {
    String name;

    List<GoodsSpecification> valueList;
}
