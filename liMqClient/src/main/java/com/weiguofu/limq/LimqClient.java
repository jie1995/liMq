package com.weiguofu.limq;

import com.google.gson.Gson;
import com.weiguofu.limqcommon.RequestMessage;
import com.weiguofu.limqcommon.paramDto.ProduceParam;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:26
 * @Version 1.0
 */
@Slf4j
public class LimqClient {

    public void start(String port, String address) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new LiMqClientHandler());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 9003).channel();
        NettyConfig.channel = channel;
    }

    public void produce(String qName, boolean reliable, String value) {
        ProduceParam produceParam = new ProduceParam(qName, reliable, value);
        RequestMessage<ProduceParam> rm = new RequestMessage();
        rm.setMessageId(UuidUtil.generateUuid());
        rm.setMethodName("produce");
        rm.setTimestamp(System.currentTimeMillis());
        rm.setParam(produceParam);
        Gson gson = new Gson();
        log.info("投递消息:" + gson.toJson(rm));
        NettyConfig.channel.writeAndFlush(gson.toJson(rm));
    }

    public void declareQueue(String qName) {
        RequestMessage<String> rm = new RequestMessage();
        rm.setMessageId(UuidUtil.generateUuid());
        rm.setMethodName("declareQueue");
        rm.setTimestamp(System.currentTimeMillis());
        rm.setParam(qName);
        Gson gson = new Gson();
        log.info("投递消息:" + gson.toJson(rm));
        NettyConfig.channel.writeAndFlush(gson.toJson(rm));
    }

}