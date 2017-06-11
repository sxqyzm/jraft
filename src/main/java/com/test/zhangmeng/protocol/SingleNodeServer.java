package com.test.zhangmeng.protocol;

import com.test.zhangmeng.entry.AbstractEntry;
import com.test.zhangmeng.entry.AbstractEntryLog;
import com.test.zhangmeng.entry.RaftEntry;
import com.test.zhangmeng.entry.RaftEntryLogUseList;
import com.netease.cloudmusic.meta.*;
import com.test.zhangmeng.meta.ClientRpcResp;
import com.test.zhangmeng.server.RaftNetWork;
import com.test.zhangmeng.meta.ClientRpcReq;

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

    public SingleNodeServer(AbstractEntryLog<T> abstractEntryLog){
        this.entryLog=abstractEntryLog;
        this.raftNetWork=new RaftNetWork();
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
