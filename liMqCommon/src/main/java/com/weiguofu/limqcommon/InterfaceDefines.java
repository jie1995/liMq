package com.weiguofu.limqcommon;

/**
 * @Description: 基于SPI机制, 类全限定名及方法名称
 * @Author: GuoFuWei
 * @Date: 2020/9/3 22:31
 * @Version 1.0
 */
public interface InterfaceDefines {

    /**
     * 类全限定名
     */
    String CLASS_NAME = "com.weiguofu.limq.server.LiMqServerHandler";


    /**
     * 生产消息方法名
     */
    String M_PRODUCE = "produce";

    /**
     * 队列声明方法名
     */
    String M_DECLAREQUEUE = "declareQueue ";


}
