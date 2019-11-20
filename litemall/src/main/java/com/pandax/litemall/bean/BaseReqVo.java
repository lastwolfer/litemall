package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {
    T data;
    String errmsg;
    int errno;

    public static BaseReqVo ok(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    public static BaseReqVo ok(Object data){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
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
