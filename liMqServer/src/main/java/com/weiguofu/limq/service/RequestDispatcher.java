package com.weiguofu.limq.service;

import com.google.gson.Gson;
import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limqcommon.MessageWrapper;
import com.weiguofu.limqcommon.messageDto.RequestMessage;
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
        log.info("message:{}", message);
        Gson gson = new Gson();
        RequestMessage rm = gson.fromJson(message, RequestMessage.class);
        Class<LimqRequestReceive> clazz = (Class<LimqRequestReceive>) Class.forName(rm.getClassName());
        Method[] methodArray = clazz.getDeclaredMethods();
        Object obj = clazz.getConstructor().newInstance();
        for (Method m : methodArray) {
            log.info("current method name:{},receive method name:{}", m.getName(), rm.getMethodName());
            if (m.getName().equalsIgnoreCase(rm.getMethodName())) {
                log.info("reflect invoke");
                Object res = m.invoke(obj, message);
                return res;
            }
        }
        return MessageWrapper.wrapperMessage(ResponseUtil.fail());
    }
}
