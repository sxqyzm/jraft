package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.RaftEntry;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.*;
import com.netease.cloudmusic.server.RaftNetWork;

import java.util.ArrayList;
import java.util.List;

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
            return true;
    }

    /**
     * 处理其他节点发回的AppendRpc响应信息
     * @param appRpcResp
     * @param raftServerState
     * @return
     */
    public static boolean processAppenResp(AppRpcResp appRpcResp,RaftLeader raftLeader){
        if (raftLeader.serverStat==RoleEnum.LEADER){
            return processAppendResp_leader(appRpcResp,raftLeader);
            } else if (raftLeader.serverStat==RoleEnum.FOLLOWER||raftLeader.serverStat==RoleEnum.CANDIDATE){
            return processAppendResp_other(appRpcResp,raftLeader);
        }
        return true;
    }


    /**
     * 节点处理客户端发来的clientAppen请求
     * @param clientRpcReq
     * @param raftLeader
     * @return
     */
    public static boolean processClientAppenRequest(ClientRpcReq clientRpcReq, RaftLeader raftLeader){
        if (raftLeader.serverStat==RoleEnum.LEADER){
           return processClientAppen_leader(clientRpcReq,raftLeader);

        }else if (raftLeader.serverStat==RoleEnum.LEADER||raftLeader.serverStat==RoleEnum.CANDIDATE) {
            return processClientAppen_other(clientRpcReq,raftLeader);
        }
        return false;
    }

    /**
     * 节点发起选举请求
     * @param raftCandidate
     * @return
     */
    public static boolean startVote(RaftCandidate raftCandidate){
        if (raftCandidate.serverStat==RoleEnum.CANDIDATE){
            raftCandidate.startVote();
        }else{
            return false;
        }
        return true;
    }


    public static boolean doRaftTimeLoop(RaftServerContext raftServerContext){
        if (raftServerContext.getRaftServer().serverStat==RoleEnum.FOLLOWER){
            RaftFollwer raftFollwer=raftServerContext.getRaftServer();
            if (raftFollwer.receiveRpc==true){
                //收到过leader的heartbeat，重置recieveRpc
                raftFollwer.receiveRpc=false;
            }else{
                //没有，则转换成candidate，并发起选举
                raftFollwer.convertToCandidate();
                startVote(raftServerContext.getRaftServer());
            }
        }
        return true;
    }


    /*具体实现*/
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

    private static boolean processClientAppen_leader(ClientRpcReq clientRpcReq,RaftLeader raftLeader){
        AbstractEntry newEntry=new RaftEntry(clientRpcReq.getApplyOrder(),raftLeader.getEntryLog());
        for (long nodeId:RaftSystemState.nodeIds){
            AbstractEntry preEntry= (AbstractEntry) raftLeader.nextIndex.get(nodeId);
            if (preEntry==null){
                preEntry=raftLeader.getEntryLog().getEntryByIndex(0);
                raftLeader.nextIndex.put(nodeId,preEntry);
            }
            AppRpcReq appRpcReq=new AppRpcReq();
            appRpcReq.setLeaderId(raftLeader.nodeId);
            appRpcReq.setLeaderCommit(raftLeader.getCommitIndex().getIndex());
            appRpcReq.setLeaderTerm(raftLeader.currentTerm);
            appRpcReq.setPrevLogIndex(preEntry.getIndex());
            appRpcReq.setPrevLogTerm(preEntry.getTerm());
            List<AbstractEntry> abstractEntries=new ArrayList<AbstractEntry>();
            while (preEntry.next().next()!=null){
                abstractEntries.add(preEntry.next());
            }
            appRpcReq.setAppendEntrys((AbstractEntry[]) abstractEntries.toArray());
            raftLeader.getRaftNetWork().writeDIffAppenMsg(nodeId,appRpcReq);
        }
        return true;
    }

    private static boolean processClientAppen_other(ClientRpcReq clientRpcReq,RaftLeader raftLeader){
        if (raftLeader.serverStat==RoleEnum.FOLLOWER){
            raftLeader.getRaftNetWork().writeDIffAppenMsg(raftLeader.voteFor,clientRpcReq);
            return true;
        }
        else{
           return false;
        }
    }

    private static boolean processAppendResp_leader(AppRpcResp appRpcResp,RaftLeader raftLeader) {
        if (!appRpcResp.isSuccess()) {
            if (appRpcResp.getTerm() > raftLeader.currentTerm) {
                raftLeader.convertToFollower(appRpcResp.getNodeId(), appRpcResp.getTerm());
            } else {
                AppRpcReq appRpcReq=new AppRpcReq();
                AbstractEntry oldStartEntry=(AbstractEntry) raftLeader.nextIndex.get(appRpcResp.getNodeId());
                AbstractEntry newStartEntry=oldStartEntry.before();
                AbstractEntry beforeEntry=newStartEntry.before();
                appRpcReq.setPrevLogIndex(beforeEntry.getIndex());
                appRpcReq.setPrevLogTerm(beforeEntry.getTerm());
                appRpcReq.setLeaderId(raftLeader.nodeId);
                appRpcReq.setLeaderCommit(raftLeader.entryLog.getCommitIndex().getIndex());
                appRpcReq.setLeaderTerm(raftLeader.currentTerm);
                appRpcReq.setAppendEntrys(raftLeader.entryLog.getFromIndex(newStartEntry));
                raftLeader.getRaftNetWork().writeDIffAppenMsg(appRpcResp.getNodeId(),appRpcReq);
            }
        }else{
            //TODO 接到append成功响应后处理
        }
        return true;
    }


    private static boolean processAppendResp_other(AppRpcResp appRpcResp,RaftLeader raftLeader){
        return true;
    }




}
