package com.weiguofu.limq.service;

import com.google.gson.Gson;
import com.weiguofu.limq.*;
import com.weiguofu.limq.exception.CustomException;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.messageDto.requestParamDto.ProduceParam;
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

    private static final Gson gson = new Gson();


    /**
     * 点对点
     *
     * @param requestMessage
     * @return
     */
    public Object produce(String requestMessage) throws Exception {
        log.info("消息投递:{}", requestMessage);
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
        log.info("declareQueue:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        String qName = (String) rm.getParam();
        if (GlobalInitVar.allQueue.keySet().contains(qName)) {
            throw new CustomException(ResultEnum.REPEAT_QUEUE);
        }
        GlobalInitVar.allQueue.put(qName, new TaskQueue());
        return MessageWrapper.wrapperMessage(ResponseUtil.success());
    }

    public Object consume(String requestMessage) throws Exception {
        log.info("consume:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        String qName = (String) rm.getParam();
        if (!GlobalInitVar.allQueue.keySet().contains(qName)) {
            return MessageWrapper.wrapperMessage(ResponseUtil.fail(ResultEnum.NULL_QUEUE));
        }
        Object obj = GlobalInitVar.allQueue.get(qName).getQueue().take();
        return MessageWrapper.wrapperMessage(ResponseUtil.success(obj));
    }

}
