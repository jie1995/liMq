package com.weiguofu.limq;

import com.google.gson.Gson;
import com.weiguofu.limq.codeh.MessageDecoder;
import com.weiguofu.limq.codeh.MessageEncoder;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.RequestMessage;
import com.weiguofu.limq.messageDto.requestParamDto.ProduceParam;
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

    private String host;

    private int port;

    public LimqClient(String host, int port) {
        this.port = port;
        this.host = host;
        start();
    }

    public void produce(String qName, boolean reliable, String value) {
        ProduceParam produceParam = new ProduceParam(qName, reliable, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        rm.setParam(produceParam);
        rm.setMethodName(InterfaceDefines.M_PRODUCE);
        Gson gson = new Gson();
        log.info("投递消息:{}", gson.toJson(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    public void declareQueue(String qName) {
        RequestMessage<String> rm = new RequestMessage<>();
        rm.setMethodName(InterfaceDefines.M_DECLAREQUEUE);
        rm.setParam(qName);
        Gson gson = new Gson();
        log.info("declareQueue:{}", gson.toJson(rm));
        NettyHolder.channel.writeAndFlush(MessageWrapper.wrapperMessage(rm));
    }

    public void start() {
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
                                .addLast(new LiMqClientHandler());
                    }
                });

        Channel channel = bootstrap.connect(host, port).channel();
        NettyHolder.channel = channel;
    }
}