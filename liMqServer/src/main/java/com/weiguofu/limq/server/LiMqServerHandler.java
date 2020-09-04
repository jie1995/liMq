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
        log.info("s:{}", s);
        RequestMessage requestMessage = gson.fromJson(s, RequestMessage.class);
        Object res = requestDispatcher.requestHandle(requestMessage);
        ctx.channel().writeAndFlush(gson.toJson(res));
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        log.info("消息接收完毕");
        //ctx.flush().close().sync();
    }

    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端与服务端建立连接");
    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端与服务端连接关闭");
    }

    /**
     * 异常捕获处理方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        Gson gson = new Gson();
        //CustomException customException = (CustomException) cause;
        //ctx.channel().writeAndFlush(gson.toJson(ResponseUtil.fail(customException.getAnEnum())));
        ctx.channel().writeAndFlush(cause.getCause().toString());
        //ctx.close();
    }
}
