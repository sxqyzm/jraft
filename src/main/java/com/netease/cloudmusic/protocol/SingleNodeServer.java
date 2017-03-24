package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.entry.RaftEntry;
import com.netease.cloudmusic.entry.RaftEntryLogUseList;
import com.netease.cloudmusic.meta.*;
import com.netease.cloudmusic.server.RaftNetWork;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * implementation of AbstractServer
 * Created by hzzhangmeng2 on 2017/2/9.
 */
public class SingleNodeServer<T> extends RaftServerState<T> implements AbstractServer<T> {
    private RaftNetWork raftNetWork;
    private Lock lock=new ReentrantLock();

    public ClientRpcResp appendEntry(ClientRpcReq<T> clientRpcReq) {
        ClientRpcResp resp=new ClientRpcResp();
        if (entryLog==null){
            resp.setCode(500);
            resp.setSucc(false);
        }else{
            AbstractEntry<T> entry=new RaftEntry<T>(clientRpcReq.getApplyOrder(),getEntryLog());
            if (entryLog.appendEntry(entry)){
                resp.setCode(200);
                resp.setSucc(true);
            }else{
                resp.setCode(500);
                resp.setSucc(false);
            }
        }
        return resp;
    }

    public SingleNodeServer(int port,AbstractEntryLog<T> abstractEntryLog){
        this.entryLog=abstractEntryLog;
        this.raftNetWork=new RaftNetWork(nodeId,port);
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
        return getEntryLog().getCommitIndex();
    }

    public AbstractEntry<T> getApplyId() {
        return getEntryLog().getApplyIndex();
    }

    public RaftNetWork getRaftNetWork() {
        return raftNetWork;
    }
}
