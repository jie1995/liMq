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
 * ->未加component（=加了component引入jar时还是不行，因为已经被优化了，源码是没有component的）
 * 配置未装配，通过静态方式直接调用，这时NettyProperties还没装配
 *
 * 所以，整合springboot，通过依赖注入方式：limqclient的时候注入NettyProperties，核心代码：
 *  @Bean
 *     public LimqClient limqClient(NettyProperties properties) {
 *         return new LimqClient(properties);
 *     }
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
