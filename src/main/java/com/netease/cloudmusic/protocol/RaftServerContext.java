package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.server.RaftNetWork;

/**
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftServerContext {
    private RaftLeader raftServer;
    private RaftNetWork raftNetWork;


    public RaftLeader getRaftServer() {
        return raftServer;
    }

    public void setRaftServer(RaftLeader raftServer) {
        this.raftServer = raftServer;
    }

    public RaftNetWork getRaftNetWork() {
        return raftNetWork;
    }

    public void setRaftNetWork(RaftNetWork raftNetWork) {
        this.raftNetWork = raftNetWork;
    }
}
