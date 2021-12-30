package com.polyu.zwq.Common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<CustomizedMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomizedMessage customizedMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(customizedMessage.getLength());
        byteBuf.writeBytes(customizedMessage.getContent());
    }
}
