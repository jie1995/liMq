package com.weiguofu.limq.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: 任务队列
 * @Author: GuoFuWei
 * @Date: 2020/9/3 9:42
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class TaskQueue {
    /**
     * 一个队列可以绑定多个topic
     */
    private Set<String> topics;

    private BlockingQueue queue;


}
