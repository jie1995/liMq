package com.weiguofu.limq.server;


import com.google.gson.Gson;
import com.weiguofu.limq.service.RequestDispatcher;
import com.weiguofu.limqcommon.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

//    注入是null
//    @Autowired
//    public LiMqServerHandler(RequestDispatcher requestDispatcher) {
//        LiMqServerHandler.requestDispatcher = requestDispatcher;
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Gson gson = new Gson();
        RequestMessage requestMessage = gson.fromJson(s, RequestMessage.class);
        Object res = requestDispatcher.requestHandle(requestMessage);
        ctx.channel().writeAndFlush(gson.toJson(res));
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("消息接收完毕");
        //ctx.flush().close().sync();
    }
}
