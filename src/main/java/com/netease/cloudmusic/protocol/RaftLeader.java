package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.meta.ClientRpcReq;

/**
 * Created by zhangmeng on 2017/3/12.
 */
public class RaftLeader<T> extends RaftCandidate<T> implements AbstractLeader {
    public RaftLeader(int port, AbstractEntryLog<T> abstractEntryLog) {
        super(port, abstractEntryLog);
    }

    @Override
    public boolean proceeClientReq(ClientRpcReq clientRpcReq) {

        return false;
    }

    @Override
    public boolean doHeartBeat() {
        return false;
    }
}
