package com.weiguofu.limq.handler;

import com.google.gson.Gson;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.service.LimqRequestReceive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: 对收到的请求进行分发
 * @Author: GuoFuWei
 * @Date: 2020/9/4 10:20
 * @Version 1.0
 */
@Slf4j
@Component
public class RequestDispatcher {

    public Object requestHandle(MessageWrapper mw) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String message = mw.getMessage();
        Gson gson = new Gson();
        RequestMessage rm = gson.fromJson(message, RequestMessage.class);
        Class<LimqRequestReceive> clazz = (Class<LimqRequestReceive>) Class.forName(rm.getClassName());
        Object obj = clazz.getConstructor().newInstance();
        Method m = clazz.getMethod(rm.getMethodName(), new Class[]{String.class, String.class});
        log.info("current method name:{}", m.getName());
        Object res = m.invoke(obj, new Object[]{message, mw.getMessageId()});
        return (MessageWrapper) res;
    }
}
