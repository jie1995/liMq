package com.weiguofu.limq.messageDto;

import com.google.gson.Gson;
import com.weiguofu.limq.UuidUtil;
import lombok.Data;

/**
 * @Description: 包装返回和请求消息
 * @Author: GuoFuWei
 * @Date: 2020/9/3 22:42
 * @Version 1.0
 */
@Data
public class MessageWrapper<T> {

    /**
     * 消息ID, UUID
     */
    private String messageId;

    /**
     * 非固定长度参数
     */
    private String message;

    /**
     * 消息产生时间戳
     */
    private Long timestamp;

    public static <T> MessageWrapper wrapperMessage(T mes) {
        Gson g = new Gson();
        MessageWrapper mw = new MessageWrapper();
        mw.setMessageId(UuidUtil.generateUuid());
        mw.setMessage(g.toJson(mes));
        mw.setTimestamp(System.currentTimeMillis());
        return mw;
    }

    /**
     * 传入mesId,是为了客户端处理响应时和请求对应
     * @param mes
     * @param mesId
     * @param <T>
     * @return
     */
    public static <T> MessageWrapper wrapperMessage(T mes,String mesId) {
        Gson g = new Gson();
        MessageWrapper mw = new MessageWrapper();
        mw.setMessageId(mesId);
        mw.setMessage(g.toJson(mes));
        mw.setTimestamp(System.currentTimeMillis());
        return mw;
    }

}
