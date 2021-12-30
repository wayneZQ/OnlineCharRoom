package com.polyu.zwq.Client;

import com.polyu.zwq.Common.MessageDecoder;
import com.polyu.zwq.Common.MessageEncoder;
import com.polyu.zwq.Server.ServerHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //decoder
        pipeline.addLast("decoder",new MessageDecoder());
        //encoder
        pipeline.addLast("encoder",new MessageEncoder());
        //BusinessHandler
        pipeline.addLast(new ServerHandler());

    }
}
