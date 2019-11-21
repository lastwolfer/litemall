package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {
    T data;
    String errmsg;
    int errno;
    public static BaseReqVo ok(Object object){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setErrmsg("成功");
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setData(object);
        return objectBaseReqVo;
    }
    public static BaseReqVo ok() {
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setErrmsg("成功");
        objectBaseReqVo.setErrno(0);
        return objectBaseReqVo;

    }


    public static BaseReqVo fail() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(-1);
        baseReqVo.setErrmsg("错误");
        return baseReqVo;
    }
    public static BaseReqVo fail(Integer errorNo, String errorMsg) {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(errorNo);
        baseReqVo.setErrmsg(errorMsg);
        return baseReqVo;
    }
}
