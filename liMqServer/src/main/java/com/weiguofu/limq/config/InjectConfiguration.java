package com.weiguofu.limq.config;

import com.weiguofu.limq.LimqServer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GuoFuWei
 * @Date: 2020/9/7 23:44
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({ServerConfig.class})
public class InjectConfiguration {


    @Bean
    public LimqServer limqServer(ServerConfig properties) {
        return new LimqServer(properties);
    }

}
