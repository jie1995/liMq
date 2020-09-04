package com.weiguofu.limqcommon;

import com.google.gson.Gson;
import com.weiguofu.limqcommon.messageDto.ResponseMessage;
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

    public static MessageWrapper wrapperMessage(ResponseMessage responseMessage) {
        Gson g = new Gson();
        MessageWrapper mw = new MessageWrapper();
        mw.setMessageId(UuidUtil.generateUuid());
        mw.setMessage(g.toJson(responseMessage));
        mw.setTimestamp(System.currentTimeMillis());
        return mw;
    }


}
