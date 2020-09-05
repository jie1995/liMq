package com.weiguofu.limq.handler;


import com.weiguofu.limq.messageDto.MessageWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.weiguofu.limq.entity.NettyHolder.excutor;
import static com.weiguofu.limq.entity.NettyHolder.waitMap;


/**
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:12
 * @Version 1.0
 */

@Slf4j
@AllArgsConstructor
public class LimqClientHandler extends SimpleChannelInboundHandler<MessageWrapper> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageWrapper mw) throws Exception {
        if (mw != null) {
            if (waitMap.keySet().contains(mw.getMessageId())) {
                //设置返回值
                waitMap.get(mw.getMessageId()).setResultValue(mw.getMessage());
                //开始处理
                excutor.execute(() ->
                        MessageConsumer.consumeProcess());
            } else {
                //说明该条消息不需要响应，直接打印
                log.info("响应消息:{}", mw.toString());
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("消息接收完毕");
        //ctx.flush().close().sync();
    }
}
