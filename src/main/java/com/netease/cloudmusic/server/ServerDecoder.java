package com.netease.cloudmusic.server;

import com.netease.cloudmusic.serial.BasicSerialIml;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class ServerDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<4)return;
        byteBuf.markReaderIndex();
        int len=byteBuf.readInt();
        if (len<0)return;
        if (byteBuf.readableBytes()<len){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] dataBytes= new byte[len];
        byteBuf.readBytes(dataBytes);
        Object oj= BasicSerialIml.ByteToObject(dataBytes);
        list.add(oj);
    }
}
