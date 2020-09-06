package com.weiguofu.limq.facade;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/6 21:53
 * @Version 1.0
 */
public class Queue {
    /**
     * 队列名称
     */
    public String qName;

    /**
     * 消息可靠落地
     */
    public boolean reliable;


    public Queue(String qName) {
        this.qName = qName;
    }

    public Queue(String qName, boolean reliable) {
        this.qName = qName;
        this.reliable = reliable;
    }
}
