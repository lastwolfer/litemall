package com.pandax.litemall;

import com.pandax.litemall.mapper.GoodsMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LitemallApplicationTests {


    @Test
    void logging() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:logging.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        String username = "username";
        String password = "password";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
    }

}
