package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.meta.VoteRpcResp;
import com.netease.cloudmusic.server.RaftNetWork;

/**
 * Created by hzzhangmeng2 on 2017/3/9.
 */
public class RaftProtocol implements AbstractRaftProtocol {

    /**
     * 节点作为candidate角色发起选举操作
     * @param raftNetWork
     * @return
     */
    public static boolean startVote(RaftNetWork raftNetWork,VoteRpcReq voteRpcReq){
        raftNetWork.writeMsg(voteRpcReq);
        return true;
    }
    /**
     * 节点处理candidate发来的voteRpc请求
     * @param voteRpcReq
     * @param raftServerState
     * @return
     */
    public static boolean processVoteRequest(VoteRpcReq voteRpcReq,RaftServerState raftServerState) {
        if (raftServerState.serverStat== RoleEnum.FOLLOWER){
          return   processVoteReq_follower(voteRpcReq, raftServerState);
        }else if (raftServerState.serverStat==RoleEnum.LEADER||raftServerState.serverStat==RoleEnum.CANDIDATE){
            return processVoteReq_other(voteRpcReq, raftServerState);
        }
        return false;
    }

    /**
     * 节点处理其他节点发回的选举响应信息
     * @param voteRpcResp
     * @param raftServerState
     * @return
     */
    public static boolean processVoteResp(VoteRpcResp voteRpcResp,RaftServerState raftServerState){
            if (raftServerState.serverStat == RoleEnum.CANDIDATE) {
                if (voteRpcResp.isVoteGranted() == false) {
                    if (voteRpcResp.getTerm() > raftServerState.currentTerm) {
                        raftServerState.convertToFollower(0L, voteRpcResp.getTerm());
                    }
                } else {
                    raftServerState.votedNum++;
                }
            }
        return true;
    }

    /**
     * 节点处理日志appendRpc请求，该RPC同时可作为leader的heartBeat
     * @param appRpcReq
     * @param raftServerState
     * @return
     */
    public static boolean processAppenRequest(AppRpcReq appRpcReq,RaftServerState raftServerState) {
        if (raftServerState.serverStat==RoleEnum.FOLLOWER){
            return processAppenReq_follower(appRpcReq, raftServerState);
        }else if (raftServerState.serverStat==RoleEnum.LEADER||raftServerState.serverStat==RoleEnum.CANDIDATE){
            return processAppenReq_other(appRpcReq, raftServerState);
        }
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
            raftServerState.convertToFollower(voteRpcReq.getCandidateId(),voteRpcReq.getCandidateTerm());
            return processVoteReq_follower(voteRpcReq,raftServerState);
        }
        return false;
    }

    /**
     * 1. Reply false if term < currentTerm (§5.1)
     * 2. Reply false if log doesn’t contain an entry at prevLogIndex
     whose term matches prevLogTerm (§5.3)
     * 3. If an existing entry conflicts with a new one (same index
     but different terms), delete the existing entry and all that
     follow it (§5.3)
     * 4. Append any new entries not already in the log
     * 5. If leaderCommit > commitIndex, set commitIndex =
     min(leaderCommit, index of last new entry)
     */
    private static boolean processAppenReq_follower(AppRpcReq appRpcReq,RaftServerState raftServerState){
        //conditon 1
        if (raftServerState.currentTerm<=appRpcReq.getLeaderTerm()){
            AbstractEntry preEntry=raftServerState.entryLog.getEntryByIndex(appRpcReq.getPrevLogIndex());
            //conditon 2
            if (preEntry!=null&&preEntry.getTerm()==appRpcReq.getLeaderTerm()){
                //有待添加的entry
                AbstractEntry entryList[]=appRpcReq.getAppendEntrys();
                if (entryList!=null&&entryList.length>0){
                    int startApeend=0;
                    //遍历跳过已填加的entry或者去除follower存在conflict的entry
                    while (preEntry.next().next()!=null&&startApeend<entryList.length){
                        if (preEntry.next().getTerm()!=entryList[startApeend].getTerm()){
                            raftServerState.entryLog.deleteBackEntrys(preEntry.next());
                        }else{
                            preEntry=preEntry.next();
                            startApeend++;
                        }
                    }
                    //preEntry是最后一个entry，直接将待添加的entry[]加到preEntry后面
                    for (int i=startApeend;i<entryList.length;i++){
                        raftServerState.entryLog.appendEntry(entryList[i]);
                    }
                }
                raftServerState.updateAfterAppend(appRpcReq);
                return true;
            }
        }
        return false;
    }

    private static boolean processAppenReq_other(AppRpcReq appRpcReq,RaftServerState raftServerState){
        if (raftServerState.currentTerm<appRpcReq.getLeaderTerm()){
            raftServerState.convertToFollower(appRpcReq.getLeaderId(),appRpcReq.getLeaderTerm());
            return processAppenReq_follower(appRpcReq,raftServerState);
        }
        return false;
    }

}
