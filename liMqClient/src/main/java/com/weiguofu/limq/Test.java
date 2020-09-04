package com.weiguofu.limq;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/4 17:12
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        LimqClient limqClient = new LimqClient();
        try {
            limqClient.start(null, null);
            limqClient.declareQueue("testQueue".trim());
            limqClient.produce("testQueue", false, "hello,world");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
