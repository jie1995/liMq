package com.weiguofu.limqcommon.messageDto.requestParamDto;

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

    public String qName;
    public Boolean reliable;
    public String value;

    public String getQName() {
        return qName;
    }

    public void setQName(String qName) {
        this.qName = qName;
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
