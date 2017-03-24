package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.protocol.RaftProtocol;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 定时器执行单元，用于发起选举等操作
 * Created by hzzhangmeng2 on 2017/03/24.
 */
public class RaftTimer implements Runnable {

    private RaftTimerLoop raftTimerLoop;


    public RaftTimer(RaftTimerLoop raftTimerLoop){
        this.raftTimerLoop=raftTimerLoop;
    }

    public void run() {
        RaftProtocol.doRaftTimeLoop(raftTimerLoop.getRaftServerContext());
        raftTimerLoop.schedule(new RaftTimer(raftTimerLoop),new Random().nextInt(150)+150, TimeUnit.MILLISECONDS);
    }
}
