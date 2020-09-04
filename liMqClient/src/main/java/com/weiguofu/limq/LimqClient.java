package com.weiguofu.limq;

import com.google.gson.Gson;
import com.weiguofu.limq.codeh.ResponseMessageDecoder;
import com.weiguofu.limq.codeh.RequestMessageEncoder;
import com.weiguofu.limqcommon.InterfaceDefines;
import com.weiguofu.limqcommon.MessageWrapper;
import com.weiguofu.limqcommon.Spliter;
import com.weiguofu.limqcommon.UuidUtil;
import com.weiguofu.limqcommon.messageDto.RequestMessage;
import com.weiguofu.limqcommon.messageDto.requestParamDto.ProduceParam;
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

    public void start(String port, String address) throws InterruptedException {
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
                                .addLast(new RequestMessageEncoder())
                                .addLast(new ResponseMessageDecoder())
                                .addLast(new LiMqClientHandler());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 9003).channel();
        NettyConfig.channel = channel;
    }

    public void produce(String qName, boolean reliable, String value) {
        ProduceParam produceParam = new ProduceParam(qName, reliable, value);
        RequestMessage<ProduceParam> rm = new RequestMessage<>();
        MessageWrapper<ProduceParam> mw = new MessageWrapper();
        rm.setParam(produceParam);
        rm.setMethodName(InterfaceDefines.M_PRODUCE);
        mw.setMessageId(UuidUtil.generateUuid());
        mw.setTimestamp(System.currentTimeMillis());
        Gson gson = new Gson();
        log.info("投递消息:" + gson.toJson(rm));
        NettyConfig.channel.writeAndFlush(mw);
    }

    public void declareQueue(String qName) {
        MessageWrapper<String> mw = new MessageWrapper();
        RequestMessage<String> rm = new RequestMessage<>();
        rm.setMethodName(InterfaceDefines.M_DECLAREQUEUE);
        rm.setParam(qName);
        mw.setMessageId(UuidUtil.generateUuid());
        mw.setTimestamp(System.currentTimeMillis());
        Gson gson = new Gson();
        log.info("投递消息:" + gson.toJson(rm));
        NettyConfig.channel.writeAndFlush(mw);
    }

}