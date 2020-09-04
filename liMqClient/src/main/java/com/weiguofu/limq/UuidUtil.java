package com.weiguofu.limq;

import java.util.UUID;

/**
 * @Description: 生成uuid
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:26
 * @Version 1.0
 */
public class UuidUtil {

    public static String generateUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
