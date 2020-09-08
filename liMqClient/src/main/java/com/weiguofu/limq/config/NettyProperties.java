package com.weiguofu.limq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description: 配置类
 * @Author: GuoFuWei
 * @Date: 2020/9/5 20:56
 * @Version 1.0
 */

/**
 * 加了component，注入时机就会在bean创建前，
 * 不加走spring.factories配置类，引入jar包只会走这种，时机会在后面，所以引入jar的时候会报错
 */
@ConfigurationProperties(prefix = "netty.server")
@Slf4j
public class NettyProperties {

    private String host;

    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        log.info("自动装配配置:{}", port);
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
