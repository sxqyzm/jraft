package com.netease.cloudmusic.server.bootstrap;

import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.exception.RaftException;
import com.netease.cloudmusic.protocol.RaftServerContext;
import com.netease.cloudmusic.protocol.RaftSystemState;
import com.netease.cloudmusic.server.RaftNetWork;
import com.netease.cloudmusic.server.config.RaftServerConfig;
import com.netease.cloudmusic.timeloop.RaftTimer;
import com.netease.cloudmusic.timeloop.RaftTimerLoop;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * raft协议服务启动类
 * Created by hzzhangmeng2 on 2017/3/28.
 */
public class RaftServerBootstrap {

    private RaftServerConfig raftServerConfig;

    private RaftTimerLoop raftTimerLoop;

    private RaftServerContext raftServerContext;

    private Lock lock=new ReentrantLock();

    private volatile boolean started=false;

    public RaftServerBootstrap(RaftServerConfig raftServerConfig,RaftServerContext raftServerContext){
        this.raftServerConfig=raftServerConfig;
        this.raftServerContext=raftServerContext;
        this.raftTimerLoop=new RaftTimerLoop(raftServerContext);
    }

    public void init(RoleEnum roleEnum){
        List<HostAndPort> hostAndPorts=raftServerConfig.getServers();
        HostAndPort currenNode=raftServerConfig.getCurrentServer();

        if (currenNode==null){
            throw new RaftException("no valid current host and port");
        }

        if (hostAndPorts==null||hostAndPorts.size()==0){
            throw new RaftException("no valid host and port");
        }
        long currentNodeId=0;
        for (int i=0;i<hostAndPorts.size();i++){
            HostAndPort hostAndPortTemp=hostAndPorts.get(i);
            Long nodeIdTemp=raftServerConfig.getNodeId().get(i);
                if (hostAndPortTemp.equals(raftServerConfig.getCurrentServer())){
                    currentNodeId=nodeIdTemp;
                }
            RaftSystemState.getNodeIds().add(nodeIdTemp);
            RaftSystemState.getNodeHosts().put(nodeIdTemp, hostAndPortTemp);
        }
        RaftSystemState.init(currentNodeId,hostAndPorts.size(),raftServerConfig.getCurrentServer());

        raftServerContext.getRaftServer().init(roleEnum);
    }

    public void start(RoleEnum roleEnum){
        init(roleEnum);
        raftServerContext.getRaftServer().getRaftNetWork().startNode(raftServerContext,roleEnum);
        if (roleEnum==RoleEnum.LEADER){
            raftServerContext.getRaftServer().getRaftNetWork().initConnNodes();
            raftServerContext.getRaftServer().getRaftNetWork().initFollwerNodes(raftServerContext.getRaftServer());
            raftServerContext.getRaftServer().startTimeLoop(raftServerContext);
        }
        System.out.println("server started");
    }
}
