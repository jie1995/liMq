package com.weiguofu.limq;

import com.weiguofu.limq.codeh.MessageDecoder;
import com.weiguofu.limq.codeh.MessageEncoder;
import com.weiguofu.limq.config.ServerConfig;
import com.weiguofu.limq.jop.LimqServerHandler;
import com.weiguofu.limq.jop.RequestDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: netty
 * @Author: GuoFuWei
 * @Date: 2020/9/3 14:04
 * @Version 1.0
 */
@Slf4j
public class LimqServer implements ApplicationContextAware {

    private ServerConfig serverConfig;


    public LimqServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


    public void serverListener() throws InterruptedException {
        log.info("netty服务端启动,端口号:{}", serverConfig.getPort());
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
                                    .addLast(new MessageDecoder())
                                    .addLast(new MessageEncoder())
                                    //.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                                    //.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast("LiMqServerHandler", new LimqServerHandler(new RequestDispatcher()));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(serverConfig.getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            serverListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
