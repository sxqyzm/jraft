package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;

import java.util.concurrent.atomic.AtomicLong;

/**
 * server的属性类
 * Created by hzzhangmeng2 on 2017/2/9.
 */
public class RaftServerState<T> {

        long serverId;

        //singleNodeServer state
        AtomicLong currentTerm;
        AbstractEntryLog<T> entryLog;
        AbstractEntry<T> commitIndex;
        AbstractEntry<T> applyIndex;

        //RaftServer state
        RoleEnum serverStat;
        long voteFor;
        AbstractEntry<T>[] nextIndex;
        AbstractEntry<T>[] matchIndex;
}
