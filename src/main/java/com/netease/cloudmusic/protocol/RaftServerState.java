package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.VoteRpcReq;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * server的属性类
 * Created by hzzhangmeng2 on 2017/2/9.
 */
public class RaftServerState<T> {

        public static Lock stateLcok=new ReentrantLock();

        //singleNodeServer state
        long nodeId;
        String nodeHost;
        int nodePort;

        AbstractEntryLog<T> entryLog;

        //ABstractRaftNet state
        RoleEnum serverStat;
        boolean receiveRpc=true;
        long currentTerm;
        long voteFor;
        Map<Long,AbstractEntry<T>> nextIndex;
        Map<Long,AbstractEntry<T>> matchIndex;

        /*candidate一次选举后的获取到投票数目*/
        int votedNum;


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


}
