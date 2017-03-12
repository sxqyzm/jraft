package com.netease.cloudmusic.server;

import com.netease.cloudmusic.protocol.RaftSystemState;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class RaftNetWork implements ABstractRaftNet {

    private int port;

    private Map<Long,SocketChannel> serversMap=new HashMap<Long, SocketChannel>();

    private NettyServerLoop nettyServerLoop;

    private NettyClientLoop nettyClientLoop;

    private volatile boolean started;

    public RaftNetWork(long currentNodeId,int port){
        this.port=port;
        this.nettyServerLoop=new NettyServerLoop(port);
        this.nettyClientLoop=new NettyClientLoop();
        startNode(currentNodeId);

    }

    private void startNode(long currentNodeId){
        nettyServerLoop.startServerLoop();
        nettyClientLoop.startClientLoop();
        connOtherServers(currentNodeId);
    }

    /*建立和其他节点的连接*/
    public void connOtherServers(long currentNodeId) {
        for (long nodeId:RaftSystemState.nodeIds){
            if (nodeId!=currentNodeId){
                String nodeIp=RaftSystemState.nodeIps.get(nodeId);
                int nodePort=RaftSystemState.nodePorts.get(nodeId);
                SocketChannel socketChannel=nettyClientLoop.connServer(nodeIp,nodePort);
                if (socketChannel!=null) {
                    serversMap.put(nodeId, socketChannel);
                }
            }
        }
    }

    /*向其他节点发送信息*/
    public void writeMsg(Object object){
        for (long key:serversMap.keySet()){
            try {
                serversMap.get(key).write(object);
            }catch (Exception e){
                //TODO 向其他节点发送信息时出现异常
            }
        }
    }
}
