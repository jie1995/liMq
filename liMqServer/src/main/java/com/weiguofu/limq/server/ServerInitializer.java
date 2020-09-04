package com.weiguofu.limq.server;

import com.weiguofu.limq.service.RequestDispatcher;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

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
        // 字符串解码器
        pipeline
                .addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                .addLast("LiMqServerHandler", new LiMqServerHandler(new RequestDispatcher()));
    }
}
