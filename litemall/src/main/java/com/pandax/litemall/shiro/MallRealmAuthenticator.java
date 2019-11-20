package com.pandax.litemall.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/19
 * @time 20:15
 */

public class MallRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();
        Collection<Realm> originalrealms = this.getRealms();
        MallToken token = (MallToken) authenticationToken;
        List<Realm> realms = new ArrayList<>();
        for (Realm realm: originalrealms) {
            if(realm.getName().toLowerCase().contains(token.getType())){
                realms.add(realm);
            }
        }
        return realms.size() == 1 ? this.doSingleRealmAuthentication((Realm)realms.iterator().next(), authenticationToken) : this.doMultiRealmAuthentication(realms, authenticationToken);
    }
}
