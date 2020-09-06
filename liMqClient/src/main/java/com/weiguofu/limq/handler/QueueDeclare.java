package com.weiguofu.limq.handler;

import com.weiguofu.limq.LimqClient;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.facade.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Description: 扫描所有注入的queue
 * @Author: GuoFuWei
 * @Date: 2020/9/6 22:04
 * @Version 1.0
 */
@Slf4j
public class QueueDeclare {

    public static void queueDeclareScan(ApplicationContext applicationContext) {
        Map<String, Queue> mq = applicationContext.getBeansOfType(Queue.class);
        log.info("beans个数:{}", mq.keySet().size());
        LimqClient limqClient;
        while (NettyHolder.channel == null) {
            if (NettyHolder.channel != null) {
                break;
            }
            log.info("等待建立连接");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (mq.keySet().size() > 0 && (limqClient = LimqClient.Instance()) != null) {
            mq.forEach((k, v) -> {
                        log.info("获取的bean属性:{}", v.qName);
                        limqClient.declareQueue(v.qName);
                    }
            );
        }
    }
}

