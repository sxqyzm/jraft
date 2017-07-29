package com.test.zhangmeng.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 * 通用的基于netty的服务端网络监听实现类
 */
public class NettyServerLoop {
    private int port;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup fatherLoopGroup;
    private EventLoopGroup childLoopGroup;
    private Channel serverSocketChannel = null;
    private ChannelInitializer channelInitializer;
    private Lock lock = new ReentrantLock();
    private volatile boolean started;


    public NettyServerLoop(ChannelInitializer channelInitializer, int port) {
        this.port = port;
        this.channelInitializer = channelInitializer;
    }

    public NettyServerLoop(EventLoopGroup fatherLoopGroup, EventLoopGroup childLoopGroup, ChannelInitializer channelInitializer, int port) {
        this.fatherLoopGroup = fatherLoopGroup;
        this.childLoopGroup = childLoopGroup;
        this.channelInitializer = channelInitializer;
        this.port = port;
    }

    private void initServerLoop() {
        if (serverBootstrap != null) return;
        if (fatherLoopGroup == null) {
            fatherLoopGroup = new NioEventLoopGroup(1);
        }
        if (childLoopGroup == null) {
            childLoopGroup = new NioEventLoopGroup(4);
        }
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(fatherLoopGroup, childLoopGroup).channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(channelInitializer);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_KEEPALIVE, true);
    }

    public Channel startServerLoop() {
        if (!started) {
            lock.lock();
            try {
                if (started) return serverSocketChannel;
                initServerLoop();
                serverSocketChannel = serverBootstrap.bind(port).sync().channel();
                started = true;
            } catch (InterruptedException e) {
                fatherLoopGroup.shutdownGracefully();
                childLoopGroup.shutdownGracefully();
            } finally {
                lock.unlock();
                return serverSocketChannel;
            }
        }
        return serverSocketChannel;
    }

    public void closeServerLoop() {
        if (fatherLoopGroup != null) fatherLoopGroup.shutdownGracefully();
        if (childLoopGroup != null) childLoopGroup.shutdownGracefully();
    }

}
