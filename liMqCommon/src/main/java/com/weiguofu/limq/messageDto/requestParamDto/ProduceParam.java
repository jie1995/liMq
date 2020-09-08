package com.weiguofu.limq.messageDto.requestParamDto;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 消息投递参数
 * @Author: GuoFuWei
 * @Date: 2020/9/4 9:41
 * @Version 1.0
 */
@AllArgsConstructor
public class ProduceParam implements Serializable {

    /**
     * key即 队列qName或者topic
     */
    public String key;
    public Boolean reliable;
    public String value;

    public String getKey() {
        return key;
    }

    public void setKey(String qName) {
        this.key = qName;
    }

    public Boolean getReliable() {
        return reliable;
    }

    public void setReliable(Boolean reliable) {
        this.reliable = reliable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
