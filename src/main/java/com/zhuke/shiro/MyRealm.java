package com.zhuke.shiro;

import com.zhuke.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 自定义的Realm，从DB中查找用户的授权信息
 * <p>
 * Created by ZHUKE on 2017/8/28.
 */
public class MyRealm extends AuthorizingRealm {

    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        List<String> roles = userService.queryRoles(username);
        List<String> permissions = userService.queryPermission(username);

        authorizationInfo.setRoles(roles == null ? Collections.emptySet() : new HashSet<>(roles));
        authorizationInfo.setStringPermissions(permissions == null ? Collections.emptySet() : new HashSet<>(permissions));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MyToken upToken = (MyToken) token;
        String username = upToken.getUsername();
        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        List<String> storedPasswords = userService.queryPassword(username);
        if (CollectionUtils.isEmpty(storedPasswords)) {
            throw new UnknownAccountException("Account not found with name = [" + username + "]");
        }
        String storedPassword = storedPasswords.get(0);

        return new SimpleAuthenticationInfo(username, storedPassword, username);
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
