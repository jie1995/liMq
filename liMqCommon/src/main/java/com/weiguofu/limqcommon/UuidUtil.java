package com.weiguofu.limqcommon;

import java.util.UUID;

/**
 * @Description: 生成uuid
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:26
 * @Version 1.0
 */
public class UuidUtil {

    public static String generateUuid() {
        // 注意: 这里如果使用了replacAll,消息头长度变了，粘包处理会有问题
        return UUID.randomUUID().toString();
        //.replaceAll("-", "");
    }

}
