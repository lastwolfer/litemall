package com.pandax.litemall.exception;

import com.pandax.litemall.bean.BaseReqVo;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/18
 * @time 16:28
 */

@ControllerAdvice
@ResponseBody
public class SystemExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public BaseReqVo handleAdminException(SystemException e){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(e.getErrorNo());
        baseReqVo.setErrmsg(e.getMessage());
        return baseReqVo;
    }


    /*@ExceptionHandler(AuthenticationException.class)
    public BaseReqVo handleAuthenticationException(SystemException e){
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(e.getErrorNo());
        baseReqVo.setErrmsg(e.getMessage());
        return baseReqVo;
    }*/



}
