package com.weiguofu.limq.handler;

import com.google.gson.Gson;
import com.weiguofu.limq.InterfaceDefines;
import com.weiguofu.limq.LimqClient;
import com.weiguofu.limq.UuidUtil;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.entity.ReflectDto;
import com.weiguofu.limq.facade.LimqConsumer;
import com.weiguofu.limq.facade.LimqListener;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static com.weiguofu.limq.entity.NettyHolder.excutor;
import static com.weiguofu.limq.entity.NettyHolder.waitMap;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/5 13:42
 * @Version 1.0
 */
@Slf4j
public class MessageConsumer implements ApplicationContextAware {



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        excutor.execute(() -> LimqClient.Instance());
        //收集所有的listener
        Map<String, LimqConsumer> map = applicationContext.getBeansOfType(LimqConsumer.class);
        map.forEach((k, v) -> {
            Class clazz = v.getClass();
            try {
                //这里根据方法名称获取方法，要传入方法指定参数类型,不然获取不到
                Method method = clazz.getMethod("consume", String.class);
                LimqListener annotation = method.getDeclaredAnnotation(LimqListener.class);
                if (annotation != null) {
                    String qName = annotation.listenQueue();
                    excutor.execute(() -> pullConsume(qName, method, clazz));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
    }


    private void pullConsume(String qName, Method method, Class<? extends LimqConsumer> clazz) {
        log.info("拉取消息:{}", qName);
        RequestMessage<String> rm = new RequestMessage<>();
        rm.setMethodName(InterfaceDefines.M_CONSUME);
        rm.setParam(qName);
        Gson gson = new Gson();
        log.info("pullConsume:{}", gson.toJson(rm));
        while (true) {
            if (NettyHolder.channel != null) {
                //这里请求后，不能阻塞获取等待结果，所以得想个办法
                String uuId = UuidUtil.generateUuid();
                ReflectDto rt = new ReflectDto();
                rt.setClazz(clazz);
                rt.setMethod(method);
                waitMap.put(uuId, rt);
                NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm, uuId));
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void consumeProcess() {
        while (true) {
            //刚进入的时候 waitMap肯定不为空
            waitMap.forEach((k, v) -> {
                try {
                    v.getMethod().invoke(v.getClazz().getConstructor().newInstance(), v.getResultValue());
                    //waitMap.remove()
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            try {
                if (waitMap.keySet().size() == 0) {
                    Thread.sleep(4000);
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
