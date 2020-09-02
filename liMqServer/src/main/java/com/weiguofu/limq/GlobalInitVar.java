package com.weiguofu.limq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 全局变量
 * @Author: GuoFuWei
 * @Date: 2020/9/3 0:22
 * @Version 1.0
 */
public interface GlobalInitVar {

    /**
     * 存放所有队列
     */
    Map<String, BlockingDeque<String>> allQueue = new HashMap<>();

    /**
     * 线程池
     */
    ThreadPoolExecutor excutor = new ThreadPoolExecutor(
            5,
            5,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());
}
