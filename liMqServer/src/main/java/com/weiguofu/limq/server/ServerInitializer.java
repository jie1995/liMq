package com.weiguofu.limq.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/3 18:48
 * @Version 1.0
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //类似于一个拦截器链
        ChannelPipeline pipeline = ch.pipeline();
        //对于web请求进行编解码作用
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast("LiMqServerHandler", new LiMqServerHandler());
    }
}
