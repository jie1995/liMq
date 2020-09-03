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
     * 阻塞且将线程池中异常抛出,可靠的
     *
     * @param value
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean save(TaskQueue deque, String value) throws ExecutionException, InterruptedException {
        Future<?> future = GlobalInitVar.excutor.submit(() -> {
            try {
                deque.getQueue().put(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 注意future.get()是阻塞的
        return (Boolean) future.get();
    }


    /**
     *  消息快速落地,不可靠的
     *
     * @param value
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void quickSave(TaskQueue deque, String value) {
        GlobalInitVar.excutor.execute(() -> deque.getQueue().offer(value));
    }
}
