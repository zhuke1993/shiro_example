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
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    public SessionManager sessionManager(MemcacheSessionDAO memcacheSessionDAO) {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionDAO(memcacheSessionDAO);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(Realm myReal, SessionManager sessionManager) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager(myReal);
        SecurityUtils.setSecurityManager(securityManager);

        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    /**
     * web环境下的SecurityManager
     */
    @Bean
    public SecurityManager webSecurityManager(Realm myReal, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myReal);
        SecurityUtils.setSecurityManager(securityManager);

        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager webSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);

        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/home.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");

        //url拦截规则配置，遵循 FIRST MATCH WINS 这一原则
        //配置规则详见：org.apache.shiro.web.filter.mgt.FilterChainManager.createChain()
        //  /remoting/** = authcBasic, roles[b2bClient], perms["remote:invoke:wan,lan"]
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/admin/**", "authc, roles[admin], perms[document:read,write]");
        filterChainDefinitionMap.put("/user/**", "authc, roles[user], perms[document:read]");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
