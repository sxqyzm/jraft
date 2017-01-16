package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.Protocol.RaftProtocol;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTimer implements Runnable {

    private final RaftProtocol raftProtocol;

    private RaftTimerLoop raftTimerLoop;


    public RaftTimer(RaftTimerLoop raftTimerLoop,RaftProtocol raftProtocol){
        this.raftTimerLoop=raftTimerLoop;
        this.raftProtocol = raftProtocol;
    }

    public void run() {
        raftProtocol.serverRoleHandle();
        raftTimerLoop.schedule(new RaftTimer(raftTimerLoop, raftProtocol),new Random().nextInt(150)+150, TimeUnit.MILLISECONDS);
    }
}
