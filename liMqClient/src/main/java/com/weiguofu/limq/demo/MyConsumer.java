package com.weiguofu.limq.demo;

import com.weiguofu.limq.client.LimqClient;
import com.weiguofu.limq.facade.LimqConsumer;
import com.weiguofu.limq.facade.LimqListener;
import com.weiguofu.limq.messageDto.requestParamDto.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description: demo
 * @Author: GuoFuWei
 * @Date: 2020/9/5 15:30
 * @Version 1.0
 */

@Slf4j
@Component
public class MyConsumer implements LimqConsumer {

    @Autowired
    LimqClient limqClient;

    /**
     * 声明队列
     *
     * @return
     */
    @Bean
    public Queue queue1() {
        return new Queue("testQueue").bind("topic1").bind("topic2");
    }

    /**
     * 监听队列处理业务逻辑
     * @param val
     */
    @LimqListener(listenQueue = "testQueue")
    @Override
    public void consume(String val) {
        log.info("val:{}", val);
    }

    /**
     * 消息投递
     */
    public void produce() {
        //limqClient.produce("testQueue", false, "hello,world");
        limqClient.produceWithTopic("topic1", "hello,world");
    }

}
