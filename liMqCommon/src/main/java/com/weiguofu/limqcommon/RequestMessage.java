package com.weiguofu.limqcommon;

import lombok.Data;

/**
 * @Description: 通信消息体
 * @Author: GuoFuWei
 * @Date: 2020/9/3 22:42
 * @Version 1.0
 */
@Data
public class RequestMessage {

    /**
     * 消息ID, UUID
     */
    private String messageId;

    /**
     * 消息内容，可变长字节
     */
    private String content;

    /**
     * 消息产生时间戳
     */
    private Long timestamp;

    /**
     * 方法名称
     */
    private String methodName;


    private static final String CLASS_NAME = InterfaceDefines.CLASS_NAME;
}
