package com.pandax.litemall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/16
 * @time 14:21
 */

@Data
public class Storage {

    private int id;

    private String key;

    private String name;

    private String type;

    private long size;

    private String url;

     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

     private Boolean deleted;

}
