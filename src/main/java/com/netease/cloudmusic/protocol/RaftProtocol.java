package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.VoteRpcReq;

/**
 * Created by hzzhangmeng2 on 2017/3/9.
 */
public class RaftProtocol implements AbstractRaftProtocol {


    /**
     * 处理candidate发来的voteRpc请求
     * @param voteRpcReq
     * @param raftServerState
     * @return
     */
    public static boolean processVoteRequest(VoteRpcReq voteRpcReq,RaftServerState raftServerState) {
        if (raftServerState.serverStat== RoleEnum.FOLLOWER){
          return   processVoteReq_follower(voteRpcReq,raftServerState);
        }else if (raftServerState.serverStat==RoleEnum.LEADER||raftServerState.serverStat==RoleEnum.CANDIDATE){
            return processVoteReq_other(voteRpcReq,raftServerState);
        }
        return false;
    }

    public static boolean processAppenRequest(AppRpcReq appRpcReq,RaftServerState raftServerState) {
        return false;
    }


    private static boolean processVoteReq_follower(VoteRpcReq voteRpcReq,RaftServerState raftServerState){
        if (raftServerState.currentTerm<=voteRpcReq.getCandidateTerm()){
            if (raftServerState.voteFor==0||raftServerState.voteFor==voteRpcReq.getCandidateId()){
                AbstractEntry abstractEntry=raftServerState.entryLog.getCommitIndex();
                if (abstractEntry.getTerm()<=voteRpcReq.getLastLogTerm()
                        &&abstractEntry.getIndex()<=voteRpcReq.getLastLogIndex()){
                    raftServerState.updateAfterVote(voteRpcReq);
                    return true;
                }
            }
        }
        return false;
    }




    private static boolean processVoteReq_other(VoteRpcReq voteRpcReq,RaftServerState raftServerState){
        if (raftServerState.currentTerm<voteRpcReq.getCandidateTerm()){
            raftServerState.convertToFollower(voteRpcReq.getCandidateTerm());
            return processVoteReq_follower(voteRpcReq,raftServerState);
        }
        return false;
    }
}
