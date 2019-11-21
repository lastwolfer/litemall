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
        User user = users.get(0);
        String password = user.getPassword();
        //System.out.println(users);
        if(users.size() != 0) {
            user = users.get(0);
            password = user.getPassword();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, password, getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("*");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }


}
