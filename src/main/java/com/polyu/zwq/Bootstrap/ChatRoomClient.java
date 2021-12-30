package com.polyu.zwq.Bootstrap;

import com.polyu.zwq.Client.ClientChannelInitializer;

import com.polyu.zwq.Client.ClientHandler;
import com.polyu.zwq.Common.CustomizedMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ChatRoomClient {
    private  String host;
    private  int port;
    private static final Logger logger=LoggerFactory.getLogger(ChatRoomClient.class);

    public ChatRoomClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    public void run(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            Channel channel = cf.channel();
            logger.info("client: "+channel.localAddress()+" is ready!");


            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                CustomizedMessage message=ClientHandler.SendMessage(msg,channel);
            }


        } catch (InterruptedException e) {
            logger.error("client error:{}",e.getMessage(),e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
