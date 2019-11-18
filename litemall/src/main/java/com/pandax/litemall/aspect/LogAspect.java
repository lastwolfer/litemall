package com.pandax.litemall.aspect;

import com.pandax.litemall.bean.BaseReqVo;
import com.pandax.litemall.bean.Log;
import com.pandax.litemall.exception.AdminException;
import com.pandax.litemall.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/18
 * @time 10:49
 */

@Component
@Aspect
public class LogAspect {

    @Autowired
    LogService logService;

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.pandax.litemall.controller.*.*(..))")
    public void log(){}


    /**
     * 后置通知： 在返回后执行的通知
     */
    @AfterReturning(pointcut = "log()", returning = "ret")
    public void afterReturning( Object ret) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String uri = request.getRequestURI();
        //System.out.println(uri);

//        String methodName = uri.substring(uri.lastIndexOf("/") + 1);

        //先暂定为这个
        String methodName = uri.replace("/admin/", "");

        //System.out.println(methodName);
        /*String method = request.getMethod();
        System.out.println(method);*/

        Log log = new Log();

        //本机地址获取为了IPV6的IP，这现象只有在服务器和客户端都在同一台电脑上才会出现
        //System.out.println(getIpAddr(request));
        log.setIp(getIpAddr(request));

        BaseReqVo vo = (BaseReqVo) ret;
        int errno = vo.getErrno();
        if(errno != 0) {
            log.setAdmin("匿名用户");
            log.setStatus(false);
            log.setResult(vo.getErrmsg());
        } else {
            String username = "unknow admin123";
            log.setAdmin(username);
            log.setStatus(true);
        }


        //自定义修改
        String comment = "";
        log.setComment(comment);

        boolean isNeedRecord = true;
        String action = "";
        switch (methodName) {
            default:
                action = "未知操作";
                isNeedRecord = false;
                break;
            case "auth/login" :
                action = "登录";
                break;
            case "admin/update" :
                action = "编辑管理员";
                break;
            case "admin/create" :
                action = "添加管理员";
                break;
            case "admin/delete" :
                action = "删除管理员";
                log.setResult(vo.getErrmsg());
                break;
        }
        log.setAction(action);

        //根据方法名字分类
        log.setType(1);

        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);

        //将该日志记录至数据库
        if(isNeedRecord) {
            logService.record(log);
        }
    }

    /**
     * 出现关于Admin操作异常时执行的通知
     * @param e 异常
     * @return
     */
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void afterThrowing(AdminException e){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
//        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        String methodName = uri.replace("/admin/", "");

        Log log = new Log();
        log.setIp(getIpAddr(request));

        int errno = e.getErrorNo();

        //这里写获得username的相关逻辑
        //先设置成DEFAULT ADMIN
        String username = "DEFAULT ADMIN";
        log.setAdmin(username);
        log.setStatus(false);
        log.setResult(e.getMessage());

        //自定义修改
        String comment = "";
        log.setComment(comment);

        String action = "";
        switch (methodName) {
            default:
                action = "未知操作";
                break;
            case "auth/login" :
                action = "登录";
                break;
            case "admin/update" :
                action = "编辑管理员";
                break;
            case "admin/create" :
                action = "添加管理员";
                break;
        }
        log.setAction(action);

        //根据方法名字分类
        log.setType(1);

        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);

        //将该日志记录至数据库
        logService.record(log);
    }


    /**
     * 获得访问者的IP地址
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
