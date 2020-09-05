package com.weiguofu.limq;

import com.google.gson.Gson;
import com.weiguofu.limq.annotations.LiMqConsumer;
import com.weiguofu.limq.annotations.LimqListener;
import com.weiguofu.limq.messageDto.RequestMessage;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/5 13:42
 * @Version 1.0
 */
@Slf4j
public class MessageConsumer implements ApplicationContextAware {


    /**
     * 线程池
     */
    ThreadPoolExecutor excutor = new ThreadPoolExecutor(
            5,
            5,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadPoolExecutor.AbortPolicy());


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("建立netty连接,开始监听队列...");
        excutor.execute(() -> new LimqClient("127.0.0.1", 9003));
        //收集所有的listener
        Map<String, LiMqConsumer> map = applicationContext.getBeansOfType(LiMqConsumer.class);
        map.forEach((k, v) -> {
            Class clazz = v.getClass();
            try {
                //这里根据方法名称获取方法，要传入方法指定参数类型,不然获取不到
                Method method = clazz.getMethod("consume", String.class);
                LimqListener annotation = method.getDeclaredAnnotation(LimqListener.class);
                if (annotation != null) {
                    String qName = annotation.listenQueue();
                    excutor.execute(() -> pullConsume(qName));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
    }


    private void pullConsume(String qName) {
        log.info("拉取消息:{}", qName);
        RequestMessage<String> rm = new RequestMessage<>();
        rm.setMethodName(InterfaceDefines.M_CONSUME);
        rm.setParam(qName);
        Gson gson = new Gson();
        log.info("pullConsume:{}", gson.toJson(rm));
        while (true) {
            if (NettyHolder.channel != null) {
                ChannelFuture cf = NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
                try {
                    Object o = cf.get();
                    log.info("拉取请求:{}", (MessageWrapper) o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
