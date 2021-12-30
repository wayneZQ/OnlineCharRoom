package com.polyu.zwq.Common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<CustomizedMessage> {
    /**
     * 使用ReplayingDecoder不必调用readableBytes方法
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length=byteBuf.readInt();
        byte[] content = new byte[length];

        byteBuf.readBytes(content);

        CustomizedMessage customizedMessage = new CustomizedMessage();
        customizedMessage.setLength(length);
        customizedMessage.setContent(content);
        list.add(customizedMessage);
    }
}
