package com.weiguofu.limq.config;

import com.weiguofu.limq.LimqClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/7 23:44
 * @Version 1.0
 */
@Component
@EnableConfigurationProperties({NettyProperties.class})
public class InjectConfiguration {

    @Bean
    public LimqClient limqClient() {
        return LimqClient.Instance();
    }
}
