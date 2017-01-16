package com.netease.cloudmusic.Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class AbstractRaftServer implements RaftServer {

    private Map<String,Channel> serversMap;

    public void writeMsg(ByteBuf byteBuf) {


    }

    public void readMsg(ByteBuf byteBuf) {

    }

    public void start(){
        EventLoopGroup fatherGroup=new NioEventLoopGroup(1);
        final EventLoopGroup childGroup=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(fatherGroup,childGroup).channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {

            }
        });
        bootstrap.option(ChannelOption.SO_BACKLOG,128).option(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture channelFuture= null;

        try {
            channelFuture = bootstrap.bind(22222).sync();
            Channel serverSocketChannel=channelFuture.channel();
            System.out.println("Server start");
            serverSocketChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            fatherGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }finally {
            fatherGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
