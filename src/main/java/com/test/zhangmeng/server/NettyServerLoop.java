package com.test.zhangmeng.server;

import com.test.zhangmeng.protocol.RaftServerContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 */
public class NettyServerLoop {

    private int port;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup fatherLoopGroup;
    private EventLoopGroup childLoopGroup;
    private Channel serverSocketChannel=null;
    private Lock lock=new ReentrantLock();
    private volatile boolean started;


    public NettyServerLoop(int port){
        this.port=port;
    }

    public NettyServerLoop(EventLoopGroup fatherLoopGroup,EventLoopGroup childLoopGroup,int port){
        this.fatherLoopGroup=fatherLoopGroup;
        this.childLoopGroup=childLoopGroup;
        this.port=port;
    }

    private void initServerLoop(RaftServerContext raftServerContext){
        if (serverBootstrap!=null)return;
            if (fatherLoopGroup == null) {
                fatherLoopGroup = new NioEventLoopGroup(1);
            }
            if (childLoopGroup == null) {
                childLoopGroup = new NioEventLoopGroup();
            }
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(fatherLoopGroup, childLoopGroup).channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ServerChannelInitializer(raftServerContext));
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128).option(ChannelOption.SO_KEEPALIVE, true);
    }

    public void startServerLoop(RaftServerContext raftServerContext){
        if (!started) {
            lock.lock();
            try {
                if (started)return;
                initServerLoop(raftServerContext);
                serverSocketChannel=serverBootstrap.bind(port).sync().channel();
                started=true;
            } catch (InterruptedException e) {
                fatherLoopGroup.shutdownGracefully();
                childLoopGroup.shutdownGracefully();
            }finally {
                lock.unlock();
            }
        }
    }

    public void closeServerLoop(){
        if (fatherLoopGroup!=null)fatherLoopGroup.shutdownGracefully();
        if (childLoopGroup!=null)childLoopGroup.shutdownGracefully();
    }

}
