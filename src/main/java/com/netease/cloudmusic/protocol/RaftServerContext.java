package com.netease.cloudmusic.protocol;


/**
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftServerContext {
    private RaftLeader raftServer;


    public RaftLeader getRaftServer() {
        return raftServer;
    }

    public void setRaftServer(RaftLeader raftServer) {
        this.raftServer = raftServer;
    }

    public void start(){

    }

}
