package com.weiguofu.limq;

import com.weiguofu.limq.jop.ActivateCoreJop;
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
    public ActivateCoreJop messageConsumer() {
        return new ActivateCoreJop();
    }

    public static void main(String[] args) {
        SpringApplication.run(LimqClientApplication.class, args);
    }
}
