package com.weiguofu.limq;

import lombok.Data;

import java.util.List;
import java.util.concurrent.BlockingDeque;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/3 9:42
 * @Version 1.0
 */
@Data
public class TaskQueue {
    /**
     * 一个队列可以绑定多个队列
     */
    private List<String> topics;

    private BlockingDeque ArrayBlockingQueue;

    private void bind(String topic) {
        this.topics.add(topic);
    }



}
