package com.weiguofu.limq.client;

import com.weiguofu.limq.InvokeMethodDefines;
import com.weiguofu.limq.Spliter;
import com.weiguofu.limq.UuidUtil;
import com.weiguofu.limq.codeh.MessageDecoder;
import com.weiguofu.limq.codeh.MessageEncoder;
import com.weiguofu.limq.config.NettyProperties;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.entity.ReflectDto;
import com.weiguofu.limq.facade.LimqConsumer;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.messageDto.requestParamDto.ProduceParam;
import com.weiguofu.limq.messageDto.requestParamDto.Queue;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import static com.weiguofu.limq.entity.NettyHolder.waitMap;


/**
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:26
 * @Version 1.0
 */
@Slf4j
public class LimqClient {


    public LimqClient(NettyProperties properties) {
        log.info("开始建立连接,服务端地址:{},端口号:{}", properties.getHost(), properties.getPort());
        start(properties.getHost(), properties.getPort());
    }

    public void produce(String qName, boolean reliable, String value) {
        ProduceParam produceParam = new ProduceParam(qName, reliable, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        rm.setParam(produceParam);
        rm.setMethodName(InvokeMethodDefines.M_PRODUCE);
        //log.info("produce:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    public void produceWithTopic(String topic, String value) {
        ProduceParam produceParam = new ProduceParam(topic, false, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        rm.setParam(produceParam);
        rm.setMethodName(InvokeMethodDefines.M_PRODUCE_TOPIC);
        //log.info("produceWithTopic:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    public void declareQueue(Queue queue) {
        RequestMessage<Queue> rm = new RequestMessage<>();
        rm.setMethodName(InvokeMethodDefines.M_DECLAREQUEUE);
        rm.setParam(queue);
        //log.info("declareQueue:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    public void pullConsume(String qName, Method method, Class<? extends LimqConsumer> clazz) {
        RequestMessage<String> rm = new RequestMessage<>();
        rm.setMethodName(InvokeMethodDefines.M_CONSUME);
        rm.setParam(qName);
        while (NettyHolder.channel == null) {
            if (NettyHolder.channel != null) {
                break;
            }
            log.info("consume等待建立连接");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            if (NettyHolder.channel != null) {
                //这里请求后，不能阻塞获取等待结果，所以得想个办法
                String uuId = UuidUtil.generateUuid();
                ReflectDto rt = new ReflectDto();
                rt.setClazz(clazz);
                rt.setMethod(method);
                waitMap.put(uuId, rt);
                //myConsumer.produceWithTopic();
                NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm, uuId));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void start(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline()
                                .addLast(new Spliter())
                                //.addLast(new StringDecoder(CharsetUtil.UTF_8))
                                //.addLast(new StringEncoder(CharsetUtil.UTF_8))
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new LimqClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(host, port);
        try {
            channelFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!channelFuture.isSuccess()) {
            log.error("cannot get connection from server!");
        } else {
            NettyHolder.channel = channelFuture.channel();
        }
    }
}