package com.weiguofu.limq.facade;

/**
 * @Description: 监听业务处理必须实现该接口，使用注解也有同样效果
 * @Author: GuoFuWei
 * @Date: 2020/9/5 14:54
 * @Version 1.0
 */
public interface LimqConsumer {
    /**
     *
     * @param val
     */
    void consume(String val);
}
