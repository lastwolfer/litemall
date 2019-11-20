package com.pandax.litemall.shiro;


import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 19:56
 */

public class MallSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //X-Litemall-Admin-Token
        String header1 = request.getHeader("X-cskaoyanmall-Admin-Token");
        String header2 = request.getHeader("X-Litemall-Admin-Token");
        String header = "";
        if(header != null && !"".equals(header)) {
            return header;
        }
        return super.getSessionId(request, response);
    }
}
