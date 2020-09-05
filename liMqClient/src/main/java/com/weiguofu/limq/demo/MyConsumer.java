package com.weiguofu.limq.demo;

import com.weiguofu.limq.facade.LimqConsumer;
import com.weiguofu.limq.facade.LimqListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/5 15:30
 * @Version 1.0
 */

@Slf4j
@Component
public class MyConsumer implements LimqConsumer {

    @LimqListener(listenQueue = "testQueue")
    @Override
    public void consume(String val) {
        log.info("val:{}", val);
    }
}
