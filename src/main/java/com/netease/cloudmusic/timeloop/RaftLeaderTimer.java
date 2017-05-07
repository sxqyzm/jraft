package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.protocol.RaftLeader;
import com.netease.cloudmusic.protocol.RaftProtocol;
import com.netease.cloudmusic.protocol.RaftServerContext;
import com.netease.cloudmusic.protocol.RaftSystemState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzzhangmeng2 on 2017/4/11.
 * 定时向follwer发送心跳
 */
public class RaftLeaderTimer implements Runnable {

    private RaftTimerLoop raftTimerLoop;

    public RaftLeaderTimer(RaftTimerLoop raftTimerLoop){
        this.raftTimerLoop=raftTimerLoop;
    }

    public void run() {
        System.out.println(System.currentTimeMillis());
        RaftServerContext raftServerContext=raftTimerLoop.getRaftServerContext();
        RaftLeader raftLeader=raftServerContext.getRaftServer();
        if (raftLeader.serverStat== RoleEnum.LEADER) {
            for (long nodeId : RaftSystemState.nodeIds) {
                if (nodeId != raftLeader.nodeId) {
                    AbstractEntry preEntry = (AbstractEntry) raftLeader.nextIndex.get(nodeId);
                    if (preEntry == null) {
                        preEntry = raftLeader.getEntryLog().getEntryByIndex(0);
                        raftLeader.nextIndex.put(nodeId, preEntry);
                    }
                    AppRpcReq appRpcReq = new AppRpcReq();
                    appRpcReq.setLeaderId(raftLeader.nodeId);
                    appRpcReq.setLeaderCommit(raftLeader.getCommitIndex().getIndex());
                    appRpcReq.setLeaderTerm(raftLeader.currentTerm);
                    appRpcReq.setPrevLogIndex(preEntry.getIndex());
                    appRpcReq.setPrevLogTerm(preEntry.getTerm());
                    appRpcReq.setAppendEntrys(null);
                    raftLeader.getRaftNetWork().writeDIffAppenMsg(nodeId, appRpcReq);
                    System.out.println("leader send heartBeat "+nodeId);
                }
            }
        }
        raftTimerLoop.schedule(new RaftLeaderTimer(raftTimerLoop),new Random().nextInt(1000)+90, TimeUnit.MILLISECONDS);
    }
}
