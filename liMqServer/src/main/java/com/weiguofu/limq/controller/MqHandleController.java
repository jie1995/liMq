package com.weiguofu.limq.controller;


import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.TaskQueue;
import com.weiguofu.limq.exception.CustomException;
import com.weiguofu.limq.service.OneQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:12
 * @Version 1.0
 */

@RestController
public class MqHandleController {

    @Autowired
    private OneQueueService service;

    /**
     * 点对点
     *
     * @param qName
     * @param value
     * @return
     */
    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public Object produce(String qName, String value) throws Exception {
        TaskQueue deque;
        Optional.ofNullable(deque = GlobalInitVar
                .allQueue.get(qName))
                .orElseThrow(() -> new CustomException(ResultEnum.NULL_QUEUE));
        service.save(deque, value);
        return ResponseUtil.success();
    }


    /**
     * 声明队列
     *
     * @param qName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/declareQueue", method = RequestMethod.POST)
    public Object declareQueue(String qName) throws Exception {
        if (GlobalInitVar.allQueue.keySet().contains(qName)) {
            throw new CustomException(ResultEnum.REPEAT_QUEUE);
        }
        GlobalInitVar.allQueue.put(qName, new TaskQueue());
        return ResponseUtil.success();
    }


}
