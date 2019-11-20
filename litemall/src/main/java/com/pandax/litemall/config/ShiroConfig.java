package com.pandax.litemall.config;

import com.pandax.litemall.shiro.AdminRealm;
import com.pandax.litemall.shiro.MallRealmAuthenticator;
import com.pandax.litemall.shiro.MallSessionManager;
import com.pandax.litemall.shiro.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 19:31
 */

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        //未认证通过的重定向
        factoryBean.setLoginUrl("/admin/auth/login");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //不用认证的url，(钥匙)
        map.put("/wx/**", "anon");
        map.put("/admin/auth/login", "anon");
        //map.put("/admin/auth/logout", "logout");
        map.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(map);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager manager(MallRealmAuthenticator authenticator,
                                             MallSessionManager mallSessionManager,
                                             AdminRealm adminRealm, WxRealm wxRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ArrayList<Realm> list = new ArrayList<>();
        list.add(adminRealm);
        list.add(wxRealm);
        securityManager.setSessionManager(mallSessionManager);
        securityManager.setAuthenticator(authenticator);
        securityManager.setRealms(list);
        //securityManager.setRealm(adminRealm);
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor sourceAdvisor(DefaultWebSecurityManager manager){
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(manager);
        return attributeSourceAdvisor;
    }


    @Bean
    public MallSessionManager mallSessionManager(){
        MallSessionManager mallSessionManager = new MallSessionManager();
        mallSessionManager.setDeleteInvalidSessions(true);
        return mallSessionManager;
    }

    @Bean
    public MallRealmAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        MallRealmAuthenticator authenticator = new MallRealmAuthenticator();
        ArrayList<Realm> list = new ArrayList<>();
        list.add(adminRealm);
        list.add(wxRealm);
        authenticator.setRealms(list);
        return authenticator;
    }

}
