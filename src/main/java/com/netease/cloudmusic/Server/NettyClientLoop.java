package com.netease.cloudmusic.Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 */
public class NettyClientLoop {

    private Bootstrap bootstrap;

    private EventLoopGroup clientLoopGroup;

    private boolean inited=false;

    Lock lock=new ReentrantLock();

    public NettyClientLoop(){
        
    }

    public NettyClientLoop(EventLoopGroup clientLoopGroup){
        this.clientLoopGroup=clientLoopGroup;
    }

    private void initClientLoop(){
        if (clientLoopGroup==null) {
            clientLoopGroup = new NioEventLoopGroup();
        }
        bootstrap=new Bootstrap();
        bootstrap.group(clientLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientChannelInitilizer());
    }

    public void startClientLoop(){
        if (inited)return;
        lock.lock();
        try{
            if (inited)return;
            initClientLoop();
            inited=true;
        }finally {
            lock.unlock();
        }
    }

    public SocketChannel connServer(String host, int port){
        if (!inited)startClientLoop();
        SocketChannel channel = null;
        try {
            ChannelFuture future=bootstrap.connect(host,port).sync();
            channel = (SocketChannel)future.channel();
        } catch (InterruptedException e) {
            clientLoopGroup.shutdownGracefully();
        }
        return channel;
    }

    public void closeClientLoop(){
        if (clientLoopGroup!=null)clientLoopGroup.shutdownGracefully();
    }

}
