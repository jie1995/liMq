package com.weiguofu.limq.server;


import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.TaskQueue;
import com.weiguofu.limq.exception.CustomException;
import com.weiguofu.limq.service.OneQueueService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:12
 * @Version 1.0
 */

@Component
public class LiMqServerHandler extends SimpleChannelInboundHandler<HttpObject> {

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
    public Object produce(String qName,
                          Boolean reliable,
                          String value) throws Exception {
        TaskQueue deque;
        Optional.ofNullable(deque = GlobalInitVar
                .allQueue.get(qName))
                .orElseThrow(() -> new CustomException(ResultEnum.NULL_QUEUE));
        if (reliable) {
            service.save(deque, value);
        } else {
            service.quickSave(deque, value);
        }
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


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

    }
}
