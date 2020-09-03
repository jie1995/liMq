package com.weiguofu.limqcommon;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 15173
 */
@Data
public class ResponseMessage<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 返回码
     */
    private String code;

    /**
     * 请求返回消息
     */
    private String message;

    /**
     * 返回值Json格式
     */
    private T data;


}
