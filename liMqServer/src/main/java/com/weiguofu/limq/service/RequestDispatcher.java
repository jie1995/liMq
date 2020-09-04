package com.weiguofu.limq.service;

import com.weiguofu.limq.ResponseUtil;
import com.weiguofu.limqcommon.RequestMessage;
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

    public Object requestHandle(RequestMessage requestMessage) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = requestMessage.getClassName();
        Class<LimqRequestReceive> clazz = (Class<LimqRequestReceive>) Class.forName(className);
        Method[] methodArray = clazz.getDeclaredMethods();
        Object obj = clazz.getConstructor().newInstance();
        for (Method m : methodArray) {
            if (m.getName().equals(requestMessage.getMethodName())) {
                Object res = m.invoke(obj, requestMessage.getParam().toString());
                return res;
            }
        }
        return ResponseUtil.fail();
    }
}
