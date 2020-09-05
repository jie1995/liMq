package com.weiguofu.limq.handler;


import com.weiguofu.limq.entity.NettyHolder;
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
                //resolve这里bug:每次有结果都会新开线程执行该方法
                //开始处理
                if (!NettyHolder.scanWaitMapRunning) {
                    log.info("一直打印此条信息,说明一直在提交扫描任务到线程池..");
                    excutor.execute(() ->
                            MessageConsumer.consumeProcess());
                    NettyHolder.scanWaitMapRunning = true;
                }
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
