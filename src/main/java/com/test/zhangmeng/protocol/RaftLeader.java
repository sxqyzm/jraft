package com.test.zhangmeng.protocol;

import com.test.zhangmeng.entry.AbstractEntry;
import com.test.zhangmeng.entry.AbstractEntryLog;
import com.test.zhangmeng.entry.RaftEntry;
import com.test.zhangmeng.meta.AppRpcReq;
import com.test.zhangmeng.meta.AppRpcResp;
import com.test.zhangmeng.meta.ClientRpcReq;

/**
 * Created by zhangmeng on 2017/3/12.
 */
public class RaftLeader<T> extends RaftCandidate<T> implements AbstractLeader {
    public RaftLeader(AbstractEntryLog<T> abstractEntryLog) {
        super(abstractEntryLog);
    }

    public boolean proceeClientReq(ClientRpcReq clientRpcReq) {
        if (clientRpcReq==null&&clientRpcReq.getApplyOrder()==null)return false;
        AbstractEntry newEntry=new RaftEntry(clientRpcReq.getApplyOrder(),getEntryLog());
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
        return RaftProtocol.processAppenResp(appRpcResp,this);
    }


}
