package com.weiguofu.limq.messageDto.requestParamDto;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 声明队列
 * @Author: GuoFuWei
 * @Date: 2020/9/6 21:53
 * @Version 1.0
 */
public class Queue {

    private Set<String> topicList = new HashSet<>();

    /**
     * 队列名称
     */
    private String qName;


    public Queue(String qName) {
        this.qName = qName;
    }

    public Queue bind(String topic) {
        this.topicList.add(topic);
        return this;
    }

    public Set<String> getTopicList() {
        return this.topicList;
    }


    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

}
