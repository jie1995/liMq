package com.weiguofu.limq.service;

import com.google.gson.Gson;
import com.weiguofu.limq.GlobalInitVar;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limq.ResultEnum;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.messageDto.ResponseMessage;
import com.weiguofu.limq.messageDto.requestParamDto.ProduceParam;
import com.weiguofu.limq.messageDto.requestParamDto.Queue;
import com.weiguofu.limq.storage.TaskQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;

/**
 * @Description: 请求具体处理中心
 * @Author: GuoFuWei
 * @Date: 2020/9/4 10:30
 * @Version 1.0
 */
@Slf4j
@Component
public class LimqRequestReceive {

    private MqQueueService service = new MqQueueService();

    private static final Gson gson = new Gson();


    /**
     * 点对点
     *
     * @param requestMessage
     * @return
     */
    public Object produce(String requestMessage, String uuid) throws Exception {
        log.info("produce:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        //先把里面的参数转成json,再转成ProduceParam
        ProduceParam pp = gson.fromJson(gson.toJson(rm.getParam()), ProduceParam.class);
        TaskQueue deque;
        Optional<TaskQueue> queue = Optional.ofNullable(deque = GlobalInitVar
                .allQueue.get(pp.getKey()));
        if (!queue.isPresent()) {
            return MessageWrapper.wrapperMessage(new ResponseMessage<>(ResultEnum.NULL_QUEUE.getCode(), rm.getMethodName() + ":" + pp.getKey() + " is not exist", null), uuid);
        }
        if (pp.getReliable()) {
            service.save(deque, pp.getValue());
        } else {
            service.quickSave(deque, pp.getValue());
        }
        return MessageWrapper.wrapperMessage(ResponseUtil.success(), uuid);
    }


    /**
     * 队列方式
     *
     * @param requestMessage
     * @return
     */
    public Object produceWithTopic(String requestMessage, String uuid) {
        log.info("produce:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        //先把里面的参数转成json,再转成ProduceParam
        ProduceParam pp = gson.fromJson(gson.toJson(rm.getParam()), ProduceParam.class);
        //投递消息到带有该topic的队列
        GlobalInitVar.allQueue.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().getTopics().contains(pp.getKey()))
                .forEach(e -> {
                    if (pp.getReliable()) {
                        try {
                            service.save(e.getValue(), pp.getValue());
                        } catch (ExecutionException executionException) {
                            executionException.printStackTrace();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    } else {
                        service.quickSave(e.getValue(), pp.getValue());
                    }
                });

        return MessageWrapper.wrapperMessage(ResponseUtil.success(), uuid);
    }


    /**
     * 声明队列
     *
     * @param requestMessage
     * @return
     * @throws Exception
     */
    public Object declareQueue(String requestMessage, String uuid) throws Exception {
        log.info("declareQueue:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        Queue q = gson.fromJson(gson.toJson(rm.getParam()), Queue.class);
        //Queue q = (Queue) rm.getParam(); ResponseUtil.fail(ResultEnum.REPEAT_QUEUE), uuid
        if (GlobalInitVar.allQueue.keySet().contains(q.getqName())) {
            return MessageWrapper.wrapperMessage(
                    new ResponseMessage<>(ResultEnum.REPEAT_QUEUE.getCode(), rm.getMethodName() + ":" + q.getqName() + " is repeat", null), uuid
            );
        }
        GlobalInitVar.allQueue.put(q.getqName(), new TaskQueue(q.getTopicList(), new ArrayBlockingQueue<String>(5)));
        return MessageWrapper.wrapperMessage(ResponseUtil.success(), uuid);
    }

    public Object consume(String requestMessage, String uuid) throws Exception {
        log.info("consume:{}", requestMessage);
        RequestMessage rm = gson.fromJson(requestMessage, RequestMessage.class);
        String qName = (String) rm.getParam();
        if (!GlobalInitVar.allQueue.keySet().contains(qName)) {
            return MessageWrapper.wrapperMessage(new ResponseMessage<>(ResultEnum.NULL_QUEUE.getCode(), rm.getMethodName() + ":" + qName + " is not exist", null), uuid);
        }
        //take()方法会一直阻塞，所以没有返回结果
        Object obj = GlobalInitVar.allQueue.get(qName).getQueue().poll();
        return MessageWrapper.wrapperMessage(ResponseUtil.success(obj), uuid);
    }

}
