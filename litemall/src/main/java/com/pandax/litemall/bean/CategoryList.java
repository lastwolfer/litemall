package com.pandax.litemall.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CategoryList {
    private Integer value;
    private String label;
    private List<CategoryList> children;
}
