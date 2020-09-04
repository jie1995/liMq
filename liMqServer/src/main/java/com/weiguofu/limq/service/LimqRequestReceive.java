package com.weiguofu.limq.service;

import com.google.gson.Gson;
import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.TaskQueue;
import com.weiguofu.limq.exception.CustomException;
import com.weiguofu.limqcommon.MessageWrapper;
import com.weiguofu.limqcommon.messageDto.RequestMessage;
import com.weiguofu.limqcommon.messageDto.requestParamDto.ProduceParam;
import lombok.extern.slf4j.Slf4j;
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

    private OneQueueService service = new OneQueueService();


    /**
     * 点对点
     *
     * @param requestMessage
     * @return
     */
    public Object produce(String requestMessage) throws Exception {
        log.info("消息投递:{}", requestMessage);
        Gson gson = new Gson();
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        //先把里面的参数转成json,再转成ProduceParam
        ProduceParam pp = gson.fromJson(gson.toJson(rm.getParam()), ProduceParam.class);
        TaskQueue deque;
        Optional.ofNullable(deque = GlobalInitVar
                .allQueue.get(pp.getQName()))
                .orElseThrow(() -> new CustomException(ResultEnum.NULL_QUEUE));
        if (pp.getReliable()) {
            service.save(deque, pp.getValue());
        } else {
            service.quickSave(deque, pp.getValue());
        }
        return MessageWrapper.wrapperMessage(ResponseUtil.success());
    }


    /**
     * 声明队列
     *
     * @param requestMessage
     * @return
     * @throws Exception
     */
    public Object declareQueue(String requestMessage) throws Exception {
        Gson gson = new Gson();
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        String qName = (String) rm.getParam();
        if (GlobalInitVar.allQueue.keySet().contains(qName)) {
            throw new CustomException(ResultEnum.REPEAT_QUEUE);
        }
        GlobalInitVar.allQueue.put(qName, new TaskQueue());
        return ResponseUtil.success();
    }
}
