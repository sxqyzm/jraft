package com.test.zhangmeng.meta;

import java.io.Serializable;

/**
 * Invoked by candidates to gather votes
 * Created by hzzhangmeng2 on 2017/2/8.
 */
public class VoteRpcReq implements Serializable {
    private static final long serialVersionUID = -6661002836174187210L;

    private long candidateTerm;

    private long candidateId;

    private long lastLogTerm;

    private long lastLogIndex;

    public long getLastLogIndex() {
        return lastLogIndex;
    }

    public void setLastLogIndex(long lastLogIndex) {
        this.lastLogIndex = lastLogIndex;
    }

    public long getLastLogTerm() {
        return lastLogTerm;
    }

    public void setLastLogTerm(long lastLogTerm) {
        this.lastLogTerm = lastLogTerm;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    public long getCandidateTerm() {
        return candidateTerm;
    }

    public void setCandidateTerm(long candidateTerm) {
        this.candidateTerm = candidateTerm;
    }
}
