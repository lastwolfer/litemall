package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class SystemInfo {

    private int id;
    private String keyName;
    private String keyValue;
    private String addTime;
    private String updateTime;
    private int deleted;

}