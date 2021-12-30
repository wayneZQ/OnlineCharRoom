package com.polyu.zwq.Server;


import com.polyu.zwq.Common.CustomizedMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class ServerHandler extends SimpleChannelInboundHandler<CustomizedMessage> {
    /**
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor全局的事件执行器，一个单例
     */
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /**
     * 1.将新注册的channel加入到channelGroup中
     * 2.将某客户端上线的消息转发给其他在线客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
    }

    /**
     * 1.接收并转发消息到其他客户端
     * 2.对消息进行处理以区分自己跟其他客户端
     * @param ctx
     * @param customizedMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomizedMessage customizedMessage) throws Exception {
        byte[] content=customizedMessage.getContent();
        String stringContent=new String(content,Charset.forName("utf-8"));

        logger.info("server receive message: {}",stringContent);

        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
            if(ch!=channel){
                String OtherClientContent="[client] "+channel.remoteAddress()+" sends: "+stringContent+"\n";
                try{
                    byte[] OtherClientBytes=OtherClientContent.getBytes("utf-8");
                    int length=OtherClientBytes.length;
                    CustomizedMessage Others_message = new CustomizedMessage();

                    Others_message.setLength(length);
                    Others_message.setContent(OtherClientBytes);
                    ch.writeAndFlush(Others_message);
                }catch (Exception e){
                    logger.error("Sending message to other clients error:{}",e.getMessage(),e);
                }
            }else{
                String SelfClientContent="[ I ]"+channel.remoteAddress()+" send: "+stringContent+"\n";
                try{
                    byte[] SelfClientBytes=SelfClientContent.getBytes("utf-8");
                    int length=SelfClientBytes.length;
                    CustomizedMessage Self_message = new CustomizedMessage();

                    Self_message.setLength(length);
                    Self_message.setContent(SelfClientBytes);
                    ch.writeAndFlush(Self_message);
                }catch (Exception e){
                    logger.error("Sending message to self error:{}",e.getMessage(),e);
                }
            }
        });
    }

}
