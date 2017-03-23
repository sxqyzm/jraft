package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.meta.VoteRpcResp;

/**
 * Created by hzzhangmeng2 on 2017/3/9.
 */
public class RaftCandidate<T> extends RaftFollwer<T> implements AbstractCandidate {

    public RaftCandidate(int port,AbstractEntryLog<T> abstractEntryLog){
        super(port,abstractEntryLog);
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
