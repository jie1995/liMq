package com.weiguofu.limq;

import com.weiguofu.limq.storage.TaskQueue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
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
     * 注册的topic
     */
    Set<String> allTopic = new HashSet<>();


    /**
     * 存放所有队列(包括点对点,topic队列)
     */
    Map<String, TaskQueue> allQueue = new HashMap<>();


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
