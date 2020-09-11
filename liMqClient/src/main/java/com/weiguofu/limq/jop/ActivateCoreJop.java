package com.weiguofu.limq.jop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static com.weiguofu.limq.entity.NettyHolder.excutor;

/**
 * @Description: 核心处理类
 * @Author: GuoFuWei
 * @Date: 2020/9/5 13:42
 * @Version 1.0
 */
@Slf4j
public class ActivateCoreJop implements ApplicationContextAware {

    @Autowired
    QueueDeclareJop queueDeclareJop;

    @Autowired
    MessageCosumeJop messageCosumeJop;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        excutor.execute(() -> queueDeclareJop.queueDeclareScan(applicationContext));
        excutor.execute(() -> messageCosumeJop.messageConsumeScan(applicationContext));
    }



}
