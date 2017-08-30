package com.zhuke.shiro;

import net.spy.memcached.MemcachedClient;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by ZHUKE on 2017/8/30.
 */
public class MemcacheSessionDAO extends AbstractSessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcacheSessionDAO.class);

    private static final int EXPIRE_TIME_SEC = 30 * 60;//expires after 30 min

    private MemcachedClient memcachedClient;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        storeSession(sessionId, session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) memcachedClient.get((String) sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        storeSession(session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        memcachedClient.delete((String) session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        //使用memcache的过期策略，不支持返回所有生效的session列表
        return Collections.emptySet();
    }

    private void storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        memcachedClient.set((String) id, EXPIRE_TIME_SEC, session);

        LOGGER.debug("store a new session={}.", id, session);
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
