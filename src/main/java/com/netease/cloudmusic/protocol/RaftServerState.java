package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;

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
        long currentTerm;
        private long voteFor;
        AbstractEntry<T>[] nextIndex;
        AbstractEntry<T>[] matchIndex;

        public long getVoteFor(){
                try {
                        voteForLock.lock();
                        return voteFor;
                }finally {
                        voteForLock.unlock();
                }
        }

        public void setVoteFor(long voteFor){
                try {
                        voteForLock.lock();
                        this.voteFor=voteFor;
                }finally {
                        voteForLock.unlock();
                }


        }


}
