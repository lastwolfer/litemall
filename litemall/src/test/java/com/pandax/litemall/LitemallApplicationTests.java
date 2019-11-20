package com.pandax.litemall;

import com.pandax.litemall.bean.Coupon;
import com.pandax.litemall.mapper.CouponMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        List<Coupon> coupons = couponMapper.selectByExample(null);
        System.out.println(coupons);
    }

}
