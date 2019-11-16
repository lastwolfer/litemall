package com.pandax.litemall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Log {
    private Integer id;

    private String admin;

    private String ip;

    private Integer type;

    private final Integer DEFAULT_TYPE = 1;

    private String action;

    private Boolean status;

    private final Boolean DEFAULT_STATUS = true;

    private String result;

    private final String DEFAULT_RESULT = "";

    private String comment;

    private final String DEFAULT_COMMENT = "";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private Boolean deleted;

    private final Boolean DEFAULT_DELETED = false;

    public Log(String admin, String ip, Integer type, String action, Boolean status, String result, String comment, Date addTime, Date updateTime, Boolean deleted) {
        this.admin = admin;
        this.ip = ip;
        this.type = type;
        this.action = action;
        this.status = status;
        this.result = result;
        this.comment = comment;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }

    public Log(String admin, String ip, Integer type, String action) {
        this.admin = admin;
        this.ip = ip;
        this.type = type;
        this.action = action;
        this.status = DEFAULT_STATUS;
        this.result = DEFAULT_RESULT;
        this.comment = DEFAULT_COMMENT;
        this.addTime = new Date();
        this.updateTime = new Date();
        this.deleted = DEFAULT_DELETED;

    }

    public Log() {
    }
}
