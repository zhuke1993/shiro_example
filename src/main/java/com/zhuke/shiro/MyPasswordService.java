package com.zhuke.shiro;

import org.apache.shiro.authc.credential.PasswordService;

/**
 * 自定义的密码比较算法
 * <p>
 * Created by ZHUKE on 2017/8/30.
 */
public class MyPasswordService implements PasswordService {

    @Override
    public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        return (String) plaintextPassword;
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        return encryptPassword(submittedPlaintext).equals(encrypted);
    }
}
