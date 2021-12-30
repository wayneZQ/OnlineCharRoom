package com.polyu.zwq.Bootstrap;

import com.polyu.zwq.Server.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRoomServer {
    private static final Logger logger=LoggerFactory.getLogger(ChatRoomServer.class);

    private int port;

    public ChatRoomServer(int port){
        this.port=port;
    }

    public void run(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors() / 2);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ServerChannelInitializer()); //设置channelInitializer

            ChannelFuture cf = serverBootstrap.bind(port).sync();
            logger.info("Server started on port: {}.",port);
            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            logger.error("server error,exception: {}", e.getMessage(),e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
