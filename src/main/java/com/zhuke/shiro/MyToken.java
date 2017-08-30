package com.zhuke.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by ZHUKE on 2017/8/28.
 */
public class MyToken implements AuthenticationToken {
    /**
     * The username
     */
    private String username;

    /**
     * The password, in char[] format
     */
    private String password;

    private String others;

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public MyToken(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
