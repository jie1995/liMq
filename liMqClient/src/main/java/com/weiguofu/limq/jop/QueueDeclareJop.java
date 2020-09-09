package com.weiguofu.limq.jop;

import com.weiguofu.limq.client.LimqClient;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.messageDto.requestParamDto.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Description: 扫描所有注入的queue
 * @Author: GuoFuWei
 * @Date: 2020/9/6 22:04
 * @Version 1.0
 */
@Slf4j
public class QueueDeclareJop {

    @Autowired
    private LimqClient limqClient;

    public void queueDeclareScan(ApplicationContext applicationContext) {
        Map<String, Queue> mq = applicationContext.getBeansOfType(Queue.class);
        log.info("待创建的队列个数:{}", mq.keySet().size());
        while (NettyHolder.channel == null) {
            if (NettyHolder.channel != null) {
                break;
            }
            log.info("declare等待建立连接");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (mq.keySet().size() > 0) {
            mq.forEach((k, v) -> {
                        limqClient.declareQueue(v);
                    }
            );
        }
    }
}

