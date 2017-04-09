package com.netease.cloudmusic.meta;

import java.io.Serializable;

/**
 * Created by zhangmeng on 2017/4/9.
 */
public class InitFollwerReq implements Serializable {
    private static final long serialVersionUID = 7178967523264286227L;
    private long leaderId;
    private long leaderTerm;

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public long getLeaderTerm() {
        return leaderTerm;
    }

    public void setLeaderTerm(long leaderTerm) {
        this.leaderTerm = leaderTerm;
    }
}
