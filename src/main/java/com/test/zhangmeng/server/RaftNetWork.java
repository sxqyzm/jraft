package com.test.zhangmeng.server;

import com.test.zhangmeng.enums.RoleEnum;
import com.test.zhangmeng.exception.RaftException;
import com.test.zhangmeng.meta.InitFollwerReq;
import com.test.zhangmeng.protocol.RaftLeader;
import com.test.zhangmeng.protocol.RaftServerContext;
import com.test.zhangmeng.protocol.RaftSystemState;
import com.test.zhangmeng.server.bootstrap.HostAndPort;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class RaftNetWork implements AbstractRaftNet {

    private int port;

    private Map<Long,SocketChannel> serversMap=new HashMap<Long, SocketChannel>();

    private NettyServerLoop nettyServerLoop;

    private NettyClientLoop nettyClientLoop;

    private volatile boolean started;

    public void startNode(RaftServerContext raftServerContext, RoleEnum roleEnum){
        long currentNodeId= RaftSystemState.currentNodeId;
        HostAndPort hostAndPort=RaftSystemState.getNodeHosts().get(currentNodeId);
        if (hostAndPort==null){
            throw new RaftException("start node fail:invalid currentNodeId");
        }
        this.port=hostAndPort.getPort();
        ChannelInitializer channelInitializer = new ServerChannelInitializer(raftServerContext);
        this.nettyServerLoop=new NettyServerLoop(channelInitializer,port);
        nettyServerLoop.startServerLoop();
    }

    public void initConnNodes(){
        ChannelInitializer channelInitializer = new ClientChannelInitilizer();
        this.nettyClientLoop=new NettyClientLoop(channelInitializer);
        nettyClientLoop.startClientLoop();
        connOtherServers(RaftSystemState.currentNodeId);
    }

    /*建立和其他节点的连接*/
    public void connOtherServers(long currentNodeId) {
        for (long nodeId:RaftSystemState.getNodeIds()){
            if (nodeId!=currentNodeId){
                HostAndPort hostAndPort=RaftSystemState.getNodeHosts().get(nodeId);
                SocketChannel socketChannel=nettyClientLoop.connServer(hostAndPort.getHost(),hostAndPort.getPort());
                if (socketChannel!=null) {
                    serversMap.put(nodeId, socketChannel);
                }
            }
        }
    }

    public void initFollwerNodes(RaftLeader raftLeader){
        InitFollwerReq initFollwerReq=new InitFollwerReq();
        initFollwerReq.setLeaderId(RaftSystemState.currentNodeId);
        initFollwerReq.setLeaderTerm(raftLeader.getTerm());
        writeMsg(initFollwerReq);
    }


    /*向其他节点发送通用信息*/
    public void writeMsg(Object object){
        for (long key:serversMap.keySet()){
            try {
                serversMap.get(key).writeAndFlush(object);
            }catch (Exception e){
                //TODO 向其他节点发送信息时出现异常
            }
        }
    }

    /*向指定节点发送信息*/
    public void writeDIffAppenMsg(long nodeId,Object object){
        SocketChannel socketChannel=serversMap.get(nodeId);
        try {
            socketChannel.writeAndFlush(object);
        }catch (Exception e){
            //TODO 向其他节点发送信息时出现异常
        }
    }
}
