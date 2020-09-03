package com.weiguofu.limq.service;

import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.TaskQueue;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:41
 * @Version 1.0
 */
@Service
public class OneQueueService {

    /**
     * 异步存入消息，将线程池中异常抛出
     *
     * @param value
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean save(TaskQueue deque, String value) throws ExecutionException, InterruptedException {
        Future<?> future = GlobalInitVar.excutor.submit(() -> {
            deque.getQueue().offer(value);
        });
        // 注意future.get()是阻塞的
        return (Boolean) future.get();
    }
}
