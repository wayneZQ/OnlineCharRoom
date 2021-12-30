package com.polyu.zwq.Server;

import com.polyu.zwq.Common.MessageDecoder;
import com.polyu.zwq.Common.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //解码器
        pipeline.addLast("decoder",new MessageDecoder());
        //编码器
        pipeline.addLast("encoder",new MessageEncoder());
        //业务逻辑处理
        pipeline.addLast(new ServerHandler());
    }
}
