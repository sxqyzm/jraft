package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.Server.RaftServer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTimer implements Runnable {

    private final RaftServer raftServer;

    private RaftTimerLoop raftTimerLoop;


    public RaftTimer(RaftTimerLoop raftTimerLoop,RaftServer raftServer){
        this.raftTimerLoop=raftTimerLoop;
        this.raftServer=raftServer;
    }

    public void run() {
        raftServer.serverRoleHandle();
        System.out.println(System.currentTimeMillis());
        raftTimerLoop.schedule(new RaftTimer(raftTimerLoop,raftServer),new Random().nextInt(150)+150, TimeUnit.MILLISECONDS);
    }
}
