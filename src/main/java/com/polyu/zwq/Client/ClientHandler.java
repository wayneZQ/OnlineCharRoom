package com.polyu.zwq.Client;


import com.polyu.zwq.Common.CustomizedMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ClientHandler extends SimpleChannelInboundHandler<CustomizedMessage> {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private static Channel channel;


    /**
     * 发送消息
     * @param content
     */
    public static CustomizedMessage SendMessage(String content,Channel channel){
        CustomizedMessage customizedMessage = new CustomizedMessage();
        try{
            byte[] StringToByte=content.getBytes("utf-8");
            int length=StringToByte.length;

            customizedMessage.setLength(length);
            customizedMessage.setContent(StringToByte);
            ClientHandler.channel=channel;
            ClientHandler.channel.writeAndFlush(customizedMessage);


        } catch (UnsupportedEncodingException e) {
            logger.error("Client sends message error:{}",e.getMessage(),e);
        }
        return customizedMessage;
    }

    /**
     * 接收其他客户端的消息
     * @param ctx
     * @param customizedMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomizedMessage customizedMessage) throws Exception {
        byte[] ByteContent=customizedMessage.getContent();
        String content=new String(ByteContent, Charset.forName("utf-8"));
        logger.info("client received message:{}",content);
    }
}
