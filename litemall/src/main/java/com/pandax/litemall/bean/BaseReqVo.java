package com.pandax.litemall.bean;

import lombok.Data;

@Data
public class BaseReqVo<T> {

    T data;
    String errmsg;
    int errno;

    public static BaseReqVo ok() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    public static BaseReqVo ok(Object data){
        BaseReqVo baseReqVo = BaseReqVo.ok();
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    public static BaseReqVo fail(){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("错误");
        baseReqVo.setErrno(-1);
        return baseReqVo;
    }

    public static BaseReqVo fail(Integer errorNo, String errorMsg) {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(errorNo);
        baseReqVo.setErrmsg(errorMsg);
        return baseReqVo;
    }

    public static BaseReqVo updatedDataFailed() {
        return fail(505, "更新数据失败");
    }
    public static BaseReqVo createDataFailed() {
        return fail(510, "创建数据失败");
    }
    public static BaseReqVo badArgument() {
        return fail(401, "参数不对");
    }

}

