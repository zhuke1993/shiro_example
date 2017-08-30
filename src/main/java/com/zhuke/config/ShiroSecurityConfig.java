package com.zhuke.config;

import com.zhuke.service.UserService;
import com.zhuke.shiro.*;
import net.spy.memcached.MemcachedClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro安全框架配置
 * <p>
 * Created by ZHUKE on 2017/8/28.
 */
@Configuration
public class ShiroSecurityConfig {

    @Bean
    public Realm myReal(UserService userService, CredentialsMatcher credentialsMatcher) {
        MyRealm myRealm = new MyRealm();
        myRealm.setUserService(userService);

        myRealm.setCredentialsMatcher(credentialsMatcher);

        //设置MyRealm支持MyToken
        myRealm.setAuthenticationTokenClass(MyToken.class);

        return myRealm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher(PasswordService myPasswordService) {
        PasswordMatcher credentialsMatcher = new PasswordMatcher();
        credentialsMatcher.setPasswordService(myPasswordService);

        return credentialsMatcher;
    }

    @Bean
    public MyPasswordService myPasswordService() {
        return new MyPasswordService();
    }

    @Bean
    public MySessionIdGenerator mySessionIdGenerator() {
        return new MySessionIdGenerator();
    }

    @Bean
    public MemcacheSessionDAO memcacheSessionDAO(MemcachedClient memcachedClient, MySessionIdGenerator mySessionIdGenerator) {
        MemcacheSessionDAO memcacheSessionDAO = new MemcacheSessionDAO();
        memcacheSessionDAO.setMemcachedClient(memcachedClient);
        memcacheSessionDAO.setSessionIdGenerator(mySessionIdGenerator);
        return memcacheSessionDAO;
    }

    @Bean
    public SecurityManager securityManager(Realm myReal, MemcacheSessionDAO memcacheSessionDAO) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager(myReal);
        SecurityUtils.setSecurityManager(securityManager);

        DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
        sessionManager.setSessionDAO(memcacheSessionDAO);


        return securityManager;
    }
}
