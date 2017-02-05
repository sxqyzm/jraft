package com.netease.cloudmusic.Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.socket.SocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class AbstractRaftServer implements RaftServer {

    private int port;

    private Map<String,SocketChannel> serversMap=new HashMap<String, SocketChannel>();

    private NettyServerLoop nettyServerLoop;

    private NettyClientLoop nettyClientLoop;


    public AbstractRaftServer(int port){
        this.port=port;
        this.nettyServerLoop=new NettyServerLoop(port);
        this.nettyClientLoop=new NettyClientLoop();

    }

    public void startNode(){
        nettyServerLoop.startServerLoop();
        nettyClientLoop.startClientLoop();
    }

    public int connOtherServers(Map<String, Map<String, String>> servers) {
        return 0;
    }

    public void writeMsg(Object obj) {

    }

    public void readMsg(Byte[] bytes) {

    }

}
