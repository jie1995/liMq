package com.weiguofu.limq.messageDto;

import com.weiguofu.limq.InterfaceDefines;
import lombok.Data;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/4 23:11
 * @Version 1.0
 */
@Data
public class RequestMessage<T> {


    /**
     * 请求参数
     */
    private T param;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 类全限定名
     */
    private final String className = InterfaceDefines.CLASS_NAME;
}
