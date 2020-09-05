package com.weiguofu.limq;

import com.weiguofu.limq.messageDto.ResponseMessage;

/**
 * @Description: response工具类
 * @Author: GuoFuWei
 * @Date: 2020/9/2 23:48
 * @Version 1.0
 */
public class ResponseUtil {

    public static ResponseMessage success() {
        ResponseMessage message = new ResponseMessage();
        message.setCode(ResultEnum.SUCCESS.getCode());
        message.setMessage(ResultEnum.SUCCESS.getMessage());
        return message;
    }


    public static <T> ResponseMessage success(T data) {
        ResponseMessage<T> message = new ResponseMessage<>();
        message.setCode(ResultEnum.SUCCESS.getCode());
        message.setMessage(ResultEnum.SUCCESS.getMessage());
        message.setData(data);
        return message;
    }

    public static ResponseMessage fail() {
        ResponseMessage message = new ResponseMessage<>();
        message.setCode(ResultEnum.FAIL.getCode());
        message.setMessage(ResultEnum.FAIL.getMessage());
        return message;
    }

    public static ResponseMessage fail(ResultEnum anEnum) {
        ResponseMessage message = new ResponseMessage<>();
        message.setCode(anEnum.getCode());
        message.setMessage(anEnum.getMessage());
        return message;
    }

    public static ResponseMessage nullQueue(String qName) {
        ResponseMessage message = new ResponseMessage<>();
        message.setCode(ResultEnum.NULL_QUEUE.getCode());
        message.setMessage(qName + ":" + ResultEnum.NULL_QUEUE.getMessage());
        return message;
    }
}
