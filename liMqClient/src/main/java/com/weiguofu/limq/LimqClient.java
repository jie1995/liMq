package com.weiguofu.limq;

import com.google.gson.Gson;
import com.weiguofu.limqcommon.RequestMessage;
import com.weiguofu.limqcommon.paramDto.ProduceParam;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:26
 * @Version 1.0
 */
public class LimqClient {
    private static final int port = 9003;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 9003).channel();

        while (true) {
            ProduceParam produceParam = new ProduceParam("queueA", true, "hello,world");
            RequestMessage<ProduceParam> rm = new RequestMessage();
            rm.setMessageId(UuidUtil.generateUuid());
            rm.setMethodName("produce");
            rm.setTimestamp(System.currentTimeMillis());
            rm.setParam(produceParam);
            Gson gson = new Gson();
            channel.writeAndFlush(gson.toJson(rm));
            Thread.sleep(2000);
        }
    }
}