package com.weiguofu.limq.jop;

import com.weiguofu.limq.client.LimqClient;
import com.weiguofu.limq.entity.ReflectDto;
import com.weiguofu.limq.facade.LimqConsumer;
import com.weiguofu.limq.facade.LimqListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import static com.weiguofu.limq.entity.NettyHolder.excutor;
import static com.weiguofu.limq.entity.NettyHolder.waitMap;

/**
 * @Description: 消息消费
 * @Author: GuoFuWei
 * @Date: 2020/9/9 9:49
 * @Version 1.0
 */
@Slf4j
public class MessageCosumeJop {

    @Autowired
    private LimqClient limqClient;


    public void messageConsumeScan(ApplicationContext applicationContext) {
        //收集所有的listener
        Map<String, LimqConsumer> map = applicationContext.getBeansOfType(LimqConsumer.class);

        map.forEach((k, v) ->

        {
            Class clazz = v.getClass();
            try {
                //这里根据方法名称获取方法，要传入方法指定参数类型,不然获取不到
                Method method = clazz.getMethod("consume", String.class);
                LimqListener annotation = method.getDeclaredAnnotation(LimqListener.class);
                if (annotation != null) {
                    String qName = annotation.listenQueue();
                    excutor.execute(() -> limqClient.pullConsume(qName, method, clazz));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 拉取到消息后的方法继续调用
     */
    public static void consumeProcess() {
        while (true) {
            //刚进入的时候 waitMap肯定不为空
            Iterator<Map.Entry<String, ReflectDto>> iterator = waitMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ReflectDto> entry = iterator.next();
                ReflectDto v = entry.getValue();
                try {
                    v.getMethod().invoke(v.getClazz().getConstructor().newInstance(), v.getResultValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                iterator.remove();
            }
            try {
                if (waitMap.keySet().size() == 0) {
                    Thread.sleep(3000);
                } else {
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
