package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntryLog;

/**
 * Created by hzzhangmeng2 on 2017/3/9.
 */
public class RaftCandidate<T> extends RaftFollwer<T> implements AbstractCandidate {

    public RaftCandidate(AbstractEntryLog<T> abstractEntryLog){
        super(abstractEntryLog);
    }

    @Override
    public void startVote() {


    }

    @Override
    public void finishVote() {

    }
}
