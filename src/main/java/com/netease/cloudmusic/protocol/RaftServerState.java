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

        private Lock voteForLock=new ReentrantLock();

        long nodeId;
        String nodeHost;
        int nodePort;
        //singleNodeServer state
        AbstractEntryLog<T> entryLog;

        //RaftServer state
        RoleEnum serverStat;
        boolean receiveRpc=true;
        long currentTerm;
        long voteFor;
        AbstractEntry<T>[] nextIndex;
        AbstractEntry<T>[] matchIndex;

        /**
         * 处理完vote更新node属性信息
         * @param voteRpcReq
         */
        public void updateAfterVote(VoteRpcReq voteRpcReq){
                currentTerm=voteRpcReq.getCandidateTerm();
                voteFor=voteRpcReq.getCandidateId();
                receiveRpc=true;
        }

        public void updateAfterAppend(AppRpcReq appRpcReq){
                currentTerm=appRpcReq.getLeaderTerm();
                receiveRpc=true;
        }


}
