package com.pandax.litemall.shiro;

import com.pandax.litemall.bean.*;
import com.pandax.litemall.mapper.AdminMapper;
import com.pandax.litemall.mapper.PermissionMapper;
import com.pandax.litemall.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 19:41
 */

@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MallToken token = (MallToken) authenticationToken;

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(token.getUsername()).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        return null;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
