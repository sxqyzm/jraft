package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.entry.RaftEntryLogUseList;
import com.netease.cloudmusic.meta.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * implementation of AbstractServer
 * Created by hzzhangmeng2 on 2017/2/9.
 */
public class SingleNodeServer<T> extends RaftServerState<T> implements AbstractServer<T> {

    private Lock lock=new ReentrantLock();

    public SingleNodeServer(){
        currentTerm=new AtomicLong(0);
    }

    public ClientRpcResp appendEntry(ClientRpcReq<T> clientRpcReq) {
        return null;
    }

    public long getCurrentTerm() {
        return currentTerm.get();
    }

    public AbstractEntryLog<T> getEntryLog() {
        if (entryLog==null) {
            try {
                lock.lock();
                if (entryLog == null) entryLog = new RaftEntryLogUseList<T>();
            } finally {
                lock.unlock();
            }
        }
        return entryLog;
    }

    public AbstractEntry<T> getCommitIndex() {
        return commitIndex;
    }

    public AbstractEntry<T> getApplyId() {
        return applyIndex;
    }

}
