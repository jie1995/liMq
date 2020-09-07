package com.weiguofu.limq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 配置类
 * @Author: GuoFuWei
 * @Date: 2020/9/5 20:56
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "netty.server")
@Slf4j
public class NettyProperties {

    public static String host;

    public static int port;

    public void setHost(String host) {
        NettyProperties.host = host;
    }


    public void setPort(int port) {
        log.info("自动装配配置:{}", port);
        NettyProperties.port = port;
    }
}
