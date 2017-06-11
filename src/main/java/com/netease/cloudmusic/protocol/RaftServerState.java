package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.server.bootstrap.HostAndPort;
import com.netease.cloudmusic.timeloop.RaftTimerLoop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * server的属性类
 * Created by hzzhangmeng2 on 2017/2/9.
 */
public class RaftServerState<T> {

        /**
         *
         */
        private RaftRegister serverState=new RaftRegister();

        public static Lock stateLcok=new ReentrantLock();

        //singleNodeServer state
        public long nodeId;
        public HostAndPort hostAndPort;

        public  AbstractEntryLog<T> entryLog;

        //AbstractRaftNet state
        public   RoleEnum serverStat;
        public  boolean receiveRpc=true;
        public long currentTerm;
        public  long voteFor;
        public  Map<Long,AbstractEntry> nextIndex=new HashMap<Long, AbstractEntry>();
        public  Map<Long,AbstractEntry> matchIndex=new HashMap<Long, AbstractEntry>();

        /*candidate一次选举后的获取到投票数目*/
        public  int votedNum;

        /*定时器是否已经启动过*/
        public  boolean initedFollwer=false;


        public void init(RoleEnum roleEnum){
                nodeId=RaftSystemState.currentNodeId;
                hostAndPort=RaftSystemState.getNodeHosts().get(nodeId);
                serverStat=roleEnum;
                currentTerm=0;
                voteFor=0;
                votedNum=0;
                for (Long id:RaftSystemState.nodeIds){
                        if (id!=nodeId) {
                                nextIndex.put(nodeId,entryLog.getEntryByIndex(0));
                                matchIndex.put(nodeId,entryLog.getEntryByIndex(0));
                        }
                }
                if (roleEnum==RoleEnum.LEADER){
                        convertToLeader();
                }
        }


        /**
         * follower处理完vote更新node属性信息
         * @param voteRpcReq
         */
        public void updateAfterVote(VoteRpcReq voteRpcReq){
                        currentTerm=voteRpcReq.getCandidateTerm();
                        voteFor=voteRpcReq.getCandidateId();
                        receiveRpc=true;
        }

        /**
         * follower处理完apeendRpc更新node属性信息
         * @param appRpcReq
         */
        public void updateAfterAppend(AppRpcReq appRpcReq){
                        currentTerm=appRpcReq.getLeaderTerm();
                        receiveRpc=true;
        }

        /*leader节点启动时触发follwer节点*/
        public void convertToFollower(long leaderId,long newTerm,RaftServerContext raftServerContext){
                serverStat=RoleEnum.FOLLOWER;
                currentTerm=newTerm;
                voteFor=leaderId;
                receiveRpc=true;
                votedNum=0;
                //启动定时器
                startTimeLoop(raftServerContext);
        }

        public void startTimeLoop(RaftServerContext raftServerContext){
                //启动定时器
                if (!initedFollwer){
                        System.out.println("init timeLoop");
                        RaftTimerLoop raftTimerLoop=new RaftTimerLoop(raftServerContext);
                        raftTimerLoop.init();
                        initedFollwer=true;
                }
        }

        /**
         * server从其他状态转变成follower，在接收到的rpc消息中的term>currentTerm的情况下
         * @param newTerm
         */
        public void convertToFollower(long leaderId,long newTerm){
                serverStat=RoleEnum.FOLLOWER;
                currentTerm=newTerm;
                voteFor=leaderId;
                receiveRpc=true;
                votedNum=0;
        }

        /**
         * server从其他状态转变成candidate
         */
        public void convertToCandidate(){
                serverStat=RoleEnum.CANDIDATE;
                currentTerm++;
                voteFor=nodeId;
                receiveRpc=false;
                votedNum=0;
        }

        /**
         * server转变成leader
         */
        public void convertToLeader(){
                serverStat=RoleEnum.LEADER;
        }

        public boolean isInitedFollwer(){
                return initedFollwer;
        }

        public long getTerm(){
                return currentTerm;
        }

        public boolean reStartCandidate(){
                this.currentTerm++;
                this.votedNum=0;
                return true;
        }

}
