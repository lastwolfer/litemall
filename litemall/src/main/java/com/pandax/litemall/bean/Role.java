package com.pandax.litemall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/15
 * @time 21:11
 */

@Data
public class Role {
    private int id;
    private String name;
    private String desc;
    private int enabled;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private int deleted;
}
