package com.weiguofu.limq;


import com.google.gson.Gson;
import com.weiguofu.limqcommon.MessageWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:12
 * @Version 1.0
 */

@Slf4j
@AllArgsConstructor
public class LiMqClientHandler extends SimpleChannelInboundHandler<MessageWrapper> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageWrapper mw) throws Exception {
        Gson gson = new Gson();
        //MessageWrapper responseMessage = gson.fromJson(s, MessageWrapper.class);
        log.info("响应消息:{}",mw.toString());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("消息接收完毕");
        //ctx.flush().close().sync();
    }
}
