package com.pandax.litemall;


import com.pandax.litemall.mapper.CouponMapper;
import com.pandax.litemall.util.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;


@SpringBootTest
class LitemallApplicationTests {

    @Autowired
    CouponMapper couponMapper;

    @Test
    void logging() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:logging.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        String username = "username";
        String password = "password";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        subject.login(token);
        boolean authenticated = subject.isAuthenticated();

    }

    @Test
    void mytest1(){
        String accessKeyId = "LTAI4Fi2VcFoNYJBTSJ6J2ny";
        String accessSecret = "t4Beuo8Z3f1ehaHXWZZCgAaMM3xP1Y";
        String bucket = "litemallpandax";
        String endPoint = "oss-cn-shanghai.aliyuncs.com";
    }
    @Test
    void mytest2(){
        String admin123 = Md5Utils.getMultiMd5("admin123");
        System.out.println(admin123);
    }

}
