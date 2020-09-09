package com.weiguofu.limq.client;


import com.google.gson.Gson;
import com.weiguofu.limq.entity.NettyHolder;
import com.weiguofu.limq.jop.MessageCosumeJop;
import com.weiguofu.limq.messageDto.MessageWrapper;
import com.weiguofu.limq.messageDto.ResponseMessage;
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
            Gson gson = new Gson();
            ResponseMessage responseMessage;
            if (!(responseMessage = gson.fromJson(mw.getMessage(), ResponseMessage.class)).getCode().equals("201")) {
                log.error(responseMessage.getMessage());
            } else {
                if (waitMap.keySet().contains(mw.getMessageId())) {
                    if (gson.fromJson(mw.getMessage(), ResponseMessage.class).getData() == null) {
                        waitMap.remove(mw.getMessageId());
                    }
                    //设置返回值
                    waitMap.get(mw.getMessageId()).setResultValue(mw.getMessage());
                    //resolve这里bug:每次有结果都会新开线程执行该方法
                    //开始处理
                    if (!NettyHolder.scanWaitMapRunning) {
                        //log.info("一直打印此条信息,说明一直在提交扫描任务到线程池..");
                        excutor.execute(() ->
                                MessageCosumeJop.consumeProcess());
                        NettyHolder.scanWaitMapRunning = true;
                    }
                } else {
                    //说明该条消息不需要响应，直接打印
                    log.info("response message:{}", responseMessage.getMessage());
                }
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //log.info("消息接收完毕");
        //ctx.flush().close().sync();
    }

    /**
     * 异常捕获处理方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("exceptionCaught捕获到异常");
        cause.printStackTrace();
    }
}
