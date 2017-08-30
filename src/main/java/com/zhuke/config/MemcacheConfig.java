package com.zhuke.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by ZHUKE on 2017/8/30.
 */
@Configuration
public class MemcacheConfig {

    @Value("${memcache.host}")
    private String host;

    @Value("${memcache.port}")
    private int port;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        return new MemcachedClient(inetSocketAddress);
    }
}
