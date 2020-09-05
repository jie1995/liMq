package com.weiguofu.limq;

import io.netty.channel.Channel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/4 16:47
 * @Version 1.0
 */
public class NettyHolder {


    public static Channel channel;

    public static  final Map<String, ReflectDto> waitMap= new LinkedHashMap<>();

    /**
     * 线程池
     */
   public static final ThreadPoolExecutor excutor = new ThreadPoolExecutor(
            5,
            5,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());

}



