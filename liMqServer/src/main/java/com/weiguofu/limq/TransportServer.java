package com.weiguofu.limq;

import com.weiguofu.limq.codeh.RequestMessageDecoder;
import com.weiguofu.limq.codeh.ResponseMessageEncoder;
import com.weiguofu.limq.server.LiMqServerHandler;
import com.weiguofu.limq.service.RequestDispatcher;
import com.weiguofu.limqcommon.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: netty
 * @Author: GuoFuWei
 * @Date: 2020/9/3 14:04
 * @Version 1.0
 */
@Slf4j
@Component
public class TransportServer implements ApplicationListener<ContextRefreshedEvent> {

    private int port=9003;

    public void serverListener() throws InterruptedException {
        log.info("netty服务端启动,端口号:{}", port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //对于相关启动信息进行封装
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    ///保持连接数
                    .option(ChannelOption.SO_BACKLOG, 3)
                    //保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //.handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new Spliter())
                                    .addLast(new RequestMessageDecoder())
                                    .addLast(new ResponseMessageEncoder())
                                    //.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                                    //.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast("LiMqServerHandler", new LiMqServerHandler(new RequestDispatcher()));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            serverListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
