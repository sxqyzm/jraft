package com.test.zhangmeng.protocol;

import com.test.zhangmeng.entry.AbstractEntry;
import com.test.zhangmeng.entry.AbstractEntryLog;
import com.test.zhangmeng.meta.VoteRpcReq;
import com.test.zhangmeng.meta.VoteRpcResp;

/**
 * Created by hzzhangmeng2 on 2017/3/9.
 */
public class RaftCandidate<T> extends RaftFollwer<T> implements AbstractCandidate {

    public RaftCandidate(AbstractEntryLog<T> abstractEntryLog){
        super(abstractEntryLog);
    }

    public void startVote() {
        VoteRpcReq voteRpcReq;
        try {
            RaftServerState.stateLcok.lock();
            voteRpcReq = new VoteRpcReq();
            voteRpcReq.setCandidateId(this.nodeId);
            voteRpcReq.setCandidateTerm(this.currentTerm);
            AbstractEntry abstractEntry=this.getEntryLog().getCommitIndex();
            voteRpcReq.setLastLogIndex(abstractEntry.getIndex());
            voteRpcReq.setLastLogTerm(abstractEntry.getTerm());
            RaftProtocol.startVote(this.getRaftNetWork(), voteRpcReq);

        }finally {
            RaftServerState.stateLcok.unlock();
        }
    }

    public void finishVote() {

    }

    public boolean processVoteResp(VoteRpcResp voteRpcResp) {
        try {
            RaftServerState.stateLcok.lock();
            RaftProtocol.processVoteResp(voteRpcResp,this);
        }finally {
            RaftServerState.stateLcok.unlock();
        }
        return true;
    }

}
