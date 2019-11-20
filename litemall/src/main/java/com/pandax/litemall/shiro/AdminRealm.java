package com.pandax.litemall.shiro;

import com.pandax.litemall.bean.Admin;
import com.pandax.litemall.bean.Permission;
import com.pandax.litemall.bean.PermissionExample;
import com.pandax.litemall.mapper.AdminMapper;
import com.pandax.litemall.mapper.PermissionMapper;
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
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminMapper adminMapper;


    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MallToken token = (MallToken) authenticationToken;
        //Subject subject = SecurityUtils.getSubject();
        Admin admin = adminMapper.selectAdminByName(token.getUsername());
        String password = admin.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin, password, getName());
        return authenticationInfo;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        //System.out.println("----------调用啦----------");
        Integer[] roleIds = admin.getRoleIds();
        List<Integer> list = new ArrayList<>();
        for (Integer roleId : roleIds) {
            list.add(roleId);
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionExample example = new PermissionExample();
        example.createCriteria().andDeletedEqualTo(false).andRoleIdIn(list);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        List<String> permissionList = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionList.add(permission.getPermission());
        }
        //System.out.println(permissions);
        authorizationInfo.addStringPermissions(permissionList);
        return authorizationInfo;
    }


}
