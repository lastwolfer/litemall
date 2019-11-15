package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {
    T data;
    String errmsg;
    int errno;
}
