package com.weiguofu.limq.storage;

import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: TODO
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

    private void bind(String topic) throws Exception {
        if (!GlobalInitVar.allTopic.contains(topic)) {
            throw new CustomException(ResultEnum.NULL_TOPIC);
        }
        this.topics.add(topic);
    }


}
