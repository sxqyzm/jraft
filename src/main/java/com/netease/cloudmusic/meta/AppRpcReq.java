package com.netease.cloudmusic.meta;

import com.netease.cloudmusic.entry.AbstractEntry;

import java.io.Serializable;

/**
 * Invoked by leader to replicate log entries; also used as heartbeat
 * Created by hzzhangmeng2 on 2017/2/8.
 */
public class AppRpcReq<T> implements Serializable {
    private static final long serialVersionUID = -3540481551612719501L;
    private long leaderTerm;
    private long leaderId;
    private long prevLogIndex;
    private long prevLogTerm;
    private AbstractEntry<T> appendEntrys[];
    private long leaderCommit;

    public long getLeaderTerm() {
        return leaderTerm;
    }

    public void setLeaderTerm(long leaderTerm) {
        this.leaderTerm = leaderTerm;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public long getPrevLogIndex() {
        return prevLogIndex;
    }

    public void setPrevLogIndex(long prevLogIndex) {
        this.prevLogIndex = prevLogIndex;
    }

    public long getPrevLogTerm() {
        return prevLogTerm;
    }

    public void setPrevLogTerm(long prevLogTerm) {
        this.prevLogTerm = prevLogTerm;
    }

    public AbstractEntry<T>[] getAppendEntrys() {
        return appendEntrys;
    }

    public void setAppendEntrys(AbstractEntry<T>[] appendEntrys) {
        this.appendEntrys = appendEntrys;
    }

    public long getLeaderCommit() {
        return leaderCommit;
    }

    public void setLeaderCommit(long leaderCommit) {
        this.leaderCommit = leaderCommit;
    }
}
