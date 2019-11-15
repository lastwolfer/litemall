package com.pandax.litemall.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private Integer id;

    private String username;

    private String password;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String avatar;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private Integer[] roleIds;
}
