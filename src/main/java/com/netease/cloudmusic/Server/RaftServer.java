package com.netease.cloudmusic.Server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public interface RaftServer {

    /**
     * 建立到raft集群中其他server的连接
     * @param servers 主机名和对应的host:port
     * @return 成功连接的数目
     */
    int connOtherServers(Map<String,Map<String,String>> servers);


    void writeMsg(Object obj);

    void readMsg(Byte[] bytes);

}
