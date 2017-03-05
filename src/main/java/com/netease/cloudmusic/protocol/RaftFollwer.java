package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.meta.VoteRpcResp;

/**
 * Raft Follower实现
 * Created by zhangmeng on 2017/2/26.
 */
public class RaftFollwer<T> extends SingleNodeServer<T> implements AbstractFollower<T> {
    public  RaftFollwer(AbstractEntryLog<T> abstractEntryLog){
        super(abstractEntryLog);
    }

    public VoteRpcResp acceptVoteRpc(VoteRpcReq voteRpcReq) {
        boolean grantVote=false;
        if (currentTerm<=voteRpcReq.getCandidateTerm()){
            if (voteFor==0||voteFor==voteRpcReq.getCandidateId()){
                AbstractEntry<T> abstractEntry=getCommitIndex();
                if (abstractEntry.getTerm()<=voteRpcReq.getLastLogTerm()
                        &&abstractEntry.getIndex()<=voteRpcReq.getLastLogIndex()){

                    grantVote=true;
                }
            }
        }
        return new VoteRpcResp(currentTerm,grantVote);
    }

    public AppRpcResp acceptAppenRpc(AppRpcReq<T> appRpcReq) {
        return null;
    }
}
