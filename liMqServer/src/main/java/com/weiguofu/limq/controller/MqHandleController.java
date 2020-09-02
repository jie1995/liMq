package com.weiguofu.limq.controller;


import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.service.OneQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.BlockingDeque;

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
        BlockingDeque deque;
        Optional.ofNullable(deque = GlobalInitVar.allQueue.get(qName)).orElseThrow(() -> new Exception());
        service.save(deque, value);
        return ResponseUtil.success();
    }
}
