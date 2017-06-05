package com.test.zhangmeng.protocol;

import com.test.zhangmeng.entry.AbstractEntryLog;
import com.test.zhangmeng.meta.AppRpcReq;
import com.test.zhangmeng.meta.AppRpcResp;
import com.test.zhangmeng.meta.VoteRpcReq;
import com.test.zhangmeng.meta.VoteRpcResp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Raft Follower实现
 * Created by zhangmeng on 2017/2/26.
 */
public class RaftFollwer<T> extends SingleNodeServer<T> implements AbstractFollower<T> {

    private RaftProtocol raftProtocol;

    public  RaftFollwer(AbstractEntryLog<T> abstractEntryLog)
    {
        super(abstractEntryLog);
        raftProtocol=new RaftProtocol();
    }

    public VoteRpcResp acceptVoteRpc(VoteRpcReq voteRpcReq) {
        try {
            RaftServerState.stateLcok.lock();
            boolean grantVote = raftProtocol.processVoteRequest(voteRpcReq,this);
            return new VoteRpcResp(currentTerm, grantVote);
        }finally {
            RaftServerState.stateLcok.unlock();
        }
    }

    public AppRpcResp acceptAppenRpc(AppRpcReq<T> appRpcReq) {
        try {
            RaftServerState.stateLcok.lock();
            boolean grantAppend = false;
            grantAppend = raftProtocol.processAppenRequest(appRpcReq,this);
            return new AppRpcResp(nodeId,currentTerm, grantAppend);
        }finally {
            RaftServerState.stateLcok.unlock();
        }
    }

}
