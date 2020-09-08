package com.weiguofu.limq;

import com.weiguofu.limq.codeh.MessageDecoder;
import com.weiguofu.limq.codeh.MessageEncoder;
import com.weiguofu.limq.config.NettyProperties;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.handler.LimqClientHandler;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.messageDto.requestParamDto.ProduceParam;
import com.weiguofu.limq.messageDto.requestParamDto.Queue;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


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

    protected void produce(String qName, boolean reliable, String value) {
        ProduceParam produceParam = new ProduceParam(qName, reliable, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        rm.setParam(produceParam);
        rm.setMethodName(InvokeMethodDefines.M_PRODUCE);
        log.info("produce:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    protected void produceWithTopic(String topic, String value) {
        ProduceParam produceParam = new ProduceParam(topic, false, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        rm.setParam(produceParam);
        rm.setMethodName(InvokeMethodDefines.M_PRODUCE_TOPIC);
        log.info("produceWithTopic:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    protected void declareQueue(Queue queue) {
        RequestMessage<Queue> rm = new RequestMessage<>();
        rm.setMethodName(InvokeMethodDefines.M_DECLAREQUEUE);
        rm.setParam(queue);
        log.info("declareQueue:{}", MessageWrapper.wrapperMessage(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
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

        Channel channel = bootstrap.connect(host, port).channel();
        NettyHolder.channel = channel;
    }
}