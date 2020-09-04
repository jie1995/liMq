package com.weiguofu.limqcommon;

import lombok.Data;

/**
 * @Description: 通信消息体
 * @Author: GuoFuWei
 * @Date: 2020/9/3 22:42
 * @Version 1.0
 */
@Data
public class RequestMessage<T> {

    /**
     * 消息ID, UUID
     */
    private String messageId;

    /**
     * 请求参数，json格式
     */
    private T param;

    /**
     * 消息产生时间戳
     */
    private Long timestamp;

    /**
     * 方法名称
     */
    private String methodName;


    private final String className = InterfaceDefines.CLASS_NAME;
}
