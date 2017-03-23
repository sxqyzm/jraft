package com.netease.cloudmusic.server.outputHandler;

import com.netease.cloudmusic.serial.BasicSerialIml;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 输出流最后一个handler，将对象序列化成字节数组，并放到bytebuf中，然后进行网络传输
 * Created by zhangmeng on 2017/03/22.
 */
public class ServerEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] doutData= BasicSerialIml.objectToByte(msg);
        int len=doutData.length;
        out.writeInt(len);
        out.writeBytes(doutData);
        ctx.writeAndFlush(out);
    }
}
