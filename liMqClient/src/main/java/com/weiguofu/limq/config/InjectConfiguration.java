package com.weiguofu.limq.config;

import com.weiguofu.limq.client.LimqClient;
import com.weiguofu.limq.jop.ActivateCoreJop;
import com.weiguofu.limq.jop.MessageCosumeJop;
import com.weiguofu.limq.jop.QueueDeclareJop;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GuoFuWei
 * @Date: 2020/9/7 23:44
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({NettyProperties.class})
public class InjectConfiguration {

    @Bean
    public ActivateCoreJop activateCoreJop() {
        return new ActivateCoreJop();
    }

    @Bean
    public LimqClient limqClient(NettyProperties properties) {
        return new LimqClient(properties);
    }


    @Bean
    public MessageCosumeJop messageCosumeJop() {
        return new MessageCosumeJop();
    }

    @Bean
    public QueueDeclareJop queueDeclareJop() {
        return new QueueDeclareJop();
    }

}
