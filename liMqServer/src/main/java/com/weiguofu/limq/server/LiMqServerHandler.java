package com.weiguofu.limq.server;


import com.google.gson.Gson;
import com.weiguofu.limq.service.RequestDispatcher;
import com.weiguofu.limqcommon.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: GuoFuWei
 * @Date: 2020/9/2 22:12
 * @Version 1.0
 */

@Slf4j
@Component
@AllArgsConstructor
public class LiMqServerHandler extends SimpleChannelInboundHandler<String> {



    private  RequestDispatcher requestDispatcher;

//    @Autowired
//    public LiMqServerHandler(RequestDispatcher requestDispatcher) {
//        LiMqServerHandler.requestDispatcher = requestDispatcher;
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Gson gson = new Gson();
        log.info("接收到消息:{}", s);
        RequestMessage requestMessage = gson.fromJson(s, RequestMessage.class);
        requestDispatcher.requestHandle(requestMessage);
    }
}
