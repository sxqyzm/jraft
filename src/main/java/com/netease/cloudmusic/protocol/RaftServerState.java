package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.VoteRpcReq;

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

        //RaftServer state
        RoleEnum serverStat;
        boolean receiveRpc=true;
        long currentTerm;
        long voteFor;
        AbstractEntry<T>[] nextIndex;
        AbstractEntry<T>[] matchIndex;


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
        public void convertToFollower(long newTerm){
                serverStat=RoleEnum.FOLLOWER;
                currentTerm=newTerm;
                voteFor=0;
        }




}
