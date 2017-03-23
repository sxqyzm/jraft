package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.entry.RaftEntry;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.meta.ClientRpcReq;

/**
 * Created by zhangmeng on 2017/3/12.
 */
public class RaftLeader<T> extends RaftCandidate<T> implements AbstractLeader {
    public RaftLeader(int port, AbstractEntryLog<T> abstractEntryLog) {
        super(port, abstractEntryLog);
    }

    public boolean proceeClientReq(ClientRpcReq clientRpcReq) {
        if (clientRpcReq==null&&clientRpcReq.getApplyOrder()==null)return false;
        AbstractEntry newEntry=new RaftEntry(clientRpcReq.getApplyOrder());
        return RaftProtocol.processClientAppenRequest(clientRpcReq,this);
    }

    public boolean doHeartBeat() {
        AppRpcReq appRpcReq=new AppRpcReq();
        appRpcReq.setAppendEntrys(null);
        appRpcReq.setLeaderTerm(currentTerm);
        appRpcReq.setLeaderCommit(getCommitIndex().getIndex());
        appRpcReq.setLeaderId(nodeId);
        appRpcReq.setPrevLogTerm(getEntryLog().getEntryByIndex(0).getTerm());
        appRpcReq.setPrevLogIndex(getEntryLog().getEntryByIndex(0).getIndex());
        getRaftNetWork().writeMsg(appRpcReq);
        return true;
    }

    public boolean processAppenResp(AppRpcResp appRpcResp) {
        return false;
    }


}
