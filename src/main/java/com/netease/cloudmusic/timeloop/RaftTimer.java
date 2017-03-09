package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.protocol.AbstractRaftProtocol;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTimer implements Runnable {

    private final AbstractRaftProtocol raftProtocol;

    private RaftTimerLoop raftTimerLoop;


    public RaftTimer(RaftTimerLoop raftTimerLoop,AbstractRaftProtocol raftProtocol){
        this.raftTimerLoop=raftTimerLoop;
        this.raftProtocol = raftProtocol;
    }

    public void run() {
        raftTimerLoop.schedule(new RaftTimer(raftTimerLoop, raftProtocol),new Random().nextInt(150)+150, TimeUnit.MILLISECONDS);
    }
}
