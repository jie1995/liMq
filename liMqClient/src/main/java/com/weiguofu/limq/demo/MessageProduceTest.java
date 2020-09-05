package com.weiguofu.limq.demo;

import com.weiguofu.limq.LimqClient;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/4 17:12
 * @Version 1.0
 */
public class MessageProduceTest {

    public static void main(String[] args) {
        LimqClient limqClient = LimqClient.Instance();
        limqClient.declareQueue("testQueue");
        limqClient.produce("testQueue", false, "hello,world");
    }

}
