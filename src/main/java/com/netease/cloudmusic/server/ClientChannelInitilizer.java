package com.netease.cloudmusic.server;

import com.netease.cloudmusic.server.outputHandler.ServerEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 */
public class ClientChannelInitilizer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ServerEncoder());
    }
}
