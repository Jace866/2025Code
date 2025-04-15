package com.easy.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Value("${spring.datasource.url}")
    private static String jdbcUrl;
    @Value("${spring.datasource.username}")
    private static String user;
    @Value("${spring.datasource.password}")
    private static String password;
    @Value("${server_id}")
    private static Integer serverId = 40;
    @Value("${status}")
    private static Integer status;

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static Integer getServerId() {
        return serverId;
    }

    public static Integer getStatus() {
        return status;
    }
}