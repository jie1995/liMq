package com.weiguofu.limq;

import com.weiguofu.limq.handler.MessageConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:17
 * @Version 1.0
 */
@SpringBootApplication
public class LimqClientApplication {
    @Bean
    public MessageConsumer messageConsumer() {
        return new MessageConsumer();
    }

    public static void main(String[] args) {
        SpringApplication.run(LimqClientApplication.class, args);
    }
}