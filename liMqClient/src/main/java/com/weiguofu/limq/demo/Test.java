package com.weiguofu.limq.demo;

import com.weiguofu.limq.LimqClient;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/4 17:12
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        LimqClient limqClient = new LimqClient("127.0.0.1", 9003);
        limqClient.declareQueue("testQueue".trim());
        //limqClient.produce("testQueue", false, "hello,world");
    }

}
