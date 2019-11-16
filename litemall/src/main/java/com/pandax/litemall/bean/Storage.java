package com.pandax.litemall.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Storage {

    private Integer id;
    private String key;
    private String name;
    private String type;
    private Integer size;
    private String url;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;

}
