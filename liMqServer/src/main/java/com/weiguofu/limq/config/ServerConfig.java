package com.weiguofu.limq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/11 22:21
 * @Version 1.0
 */
@Slf4j
@ConfigurationProperties(prefix = "limqserver")
public class ServerConfig {

    public  int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        log.info("----");
        this.port = port;
    }
}
