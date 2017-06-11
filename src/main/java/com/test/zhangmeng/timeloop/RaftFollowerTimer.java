package com.test.zhangmeng.timeloop;

import com.test.zhangmeng.protocol.RaftProtocol;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * follwer心跳接收检测定时器，如果follwer没有在时间段内收到心跳，则转变成candidate进行选举
 * Created by hzzhangmeng2 on 2017/03/24.
 */
public class RaftFollowerTimer implements Runnable {

    private RaftTimerLoop raftTimerLoop;


    public RaftFollowerTimer(RaftTimerLoop raftTimerLoop){
        this.raftTimerLoop=raftTimerLoop;
    }

    public void run() {
        RaftProtocol.doRaftTimeLoop(raftTimerLoop);
        raftTimerLoop.schedule(new RaftFollowerTimer(raftTimerLoop),new Random().nextInt(150)+150, TimeUnit.MILLISECONDS);
    }
}
