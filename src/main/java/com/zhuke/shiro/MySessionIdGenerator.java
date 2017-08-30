package com.zhuke.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ZHUKE on 2017/8/30.
 */
public class MySessionIdGenerator implements SessionIdGenerator {


    private static final String SESSION_PREFIX = "mysession-";

    @Override
    public Serializable generateId(Session session) {
        return SESSION_PREFIX + UUID.randomUUID().toString();
    }
}
