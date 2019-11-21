package com.pandax.litemall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandax.litemall.utils.UserInfo;

import java.util.Date;


public class WxCommentData {
    private String content;
    private String[] picList;
    @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss")
    private Date addTime;
    private UserInfo userInfo;
}
