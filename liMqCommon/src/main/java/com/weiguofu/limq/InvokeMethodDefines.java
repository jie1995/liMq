package com.weiguofu.limq;

/**
 * @Description: 基于SPI机制, 类全限定名及方法名称
 * @Author: GuoFuWei
 * @Date: 2020/9/3 22:31
 * @Version 1.0
 */
public interface InvokeMethodDefines {


    /**
     * 生产消息方法名
     */
    String M_PRODUCE = "produce";

    /**
     * 生产消息（topic方式）方法名
     */
    String M_PRODUCE_TOPIC = "produceWithTopic";



    /**
     * 队列声明方法名
     */
    String M_DECLAREQUEUE = "declareQueue";

    /**
     * 消费消息
     */
    String M_CONSUME = "consume";

}
