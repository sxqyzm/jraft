package com.netease.cloudmusic.meta;

import java.io.Serializable;

/**
 * Created by hzzhangmeng2 on 2017/2/8.
 */
public class VoteRpcResp implements Serializable {
    private static final long serialVersionUID = 7185082197380217525L;
    private long term;
    private boolean voteGranted;

    public boolean isVoteGranted() {
        return voteGranted;
    }

    public void setVoteGranted(boolean voteGranted) {
        this.voteGranted = voteGranted;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }
}
