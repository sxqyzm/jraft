package com.test.zhangmeng.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 * 基于netty的client网络通信类
 */
public class NettyClientLoop {

    private Bootstrap bootstrap;

    private EventLoopGroup clientLoopGroup;

    private boolean inited=false;

    private ChannelInitializer channelInitializer;

    Lock lock=new ReentrantLock();

    public NettyClientLoop(ChannelInitializer channelInitializer){
        this.channelInitializer = channelInitializer;
    }

    public NettyClientLoop(ChannelInitializer channelInitializer,EventLoopGroup clientLoopGroup){
        this.clientLoopGroup=clientLoopGroup;
        this.channelInitializer = channelInitializer;
    }

    private void initClientLoop(){
        if (clientLoopGroup==null) {
            clientLoopGroup = new NioEventLoopGroup(1);
        }
        bootstrap=new Bootstrap();
        bootstrap.group(clientLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(channelInitializer);
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
            channel = (NioSocketChannel)future.channel();
        } catch (InterruptedException e) {
            //TODO 打印异常日志
            return null;
        }
        return channel;
    }

    public void closeClientLoop(){
        if (clientLoopGroup!=null)clientLoopGroup.shutdownGracefully();
    }

}
