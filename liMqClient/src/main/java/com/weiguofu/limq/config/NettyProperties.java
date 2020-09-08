package com.weiguofu.limq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 配置类
 * @Author: GuoFuWei
 * @Date: 2020/9/5 20:56
 * @Version 1.0
 */

/**
 * 实现ApplicationContextAware接口
 * ->注入limqClient的方式->加了component,配置已经装配，
 * ->未加component,配置未装配，等待springboot自动装配机制
 * 所以，整合springboot，注入limqclient的时候注入NettyProperties
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
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
