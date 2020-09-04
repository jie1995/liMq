package com.weiguofu.limq.service;

import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.TaskQueue;
import com.weiguofu.limq.exception.CustomException;
import com.weiguofu.limqcommon.paramDto.ProduceParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Description: 请求具体处理中心
 * @Author: GuoFuWei
 * @Date: 2020/9/4 10:30
 * @Version 1.0
 */
@Slf4j
@Component
public class LimqRequestReceive {

    @Autowired
    private OneQueueService service;


    /**
     * 点对点
     *
     * @param produceParam
     * @return
     */
    public Object produce(String produceParam) throws Exception {
        log.info("消息投递:{}",produceParam.toString());
//        TaskQueue deque;
//        Optional.ofNullable(deque = GlobalInitVar
//                .allQueue.get(qName))
//                .orElseThrow(() -> new CustomException(ResultEnum.NULL_QUEUE));
//        if (reliable) {
//            service.save(deque, value);
//        } else {
//            service.quickSave(deque, value);
//        }
        return ResponseUtil.success();
    }


    /**
     * 声明队列
     *
     * @param qName
     * @return
     * @throws Exception
     */
    public Object declareQueue(String qName) throws Exception {
        if (GlobalInitVar.allQueue.keySet().contains(qName)) {
            throw new CustomException(ResultEnum.REPEAT_QUEUE);
        }
        GlobalInitVar.allQueue.put(qName, new TaskQueue());
        return ResponseUtil.success();
    }
}
