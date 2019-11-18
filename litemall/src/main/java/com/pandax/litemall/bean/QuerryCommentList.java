package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class QuerryCommentList {
    private Integer page;
    private Integer limit;
    private Integer userId;
    private Integer valueId;
    private String sort;
    private String order;
}
