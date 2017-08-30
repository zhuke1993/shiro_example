package com.zhuke.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZHUKE on 2017/8/28.
 */
@Configuration
public class DBConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestWhileIdle(true);
        //在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位
        basicDataSource.setTimeBetweenEvictionRunsMillis(1000L * 60L * 60L);
        //连接在池中保持空闲而不被空闲连接回收器线程,(如果有)回收的最小时间值，单位毫秒
        basicDataSource.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
        //指明是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        basicDataSource.setTestOnBorrow(true);
        return basicDataSource;
    }
}
