package com.netease.cloudmusic.timeloop;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.Random;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * raft协议中的定时器实现，基于netty的EventExecutor，简单粗暴
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public  class RaftTimerLoop extends SingleThreadEventExecutor {

    //定时器停止开关
    private volatile boolean closed=false;

    public RaftTimerLoop() {
        super(new DefaultEventExecutorGroup(1), new DefaultThreadFactory("timerloop"), true);
    }
    public RaftTimerLoop(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp){
        super(parent,threadFactory,addTaskWakesUp);
    }

    //初始化时添加一个定时任务
    public void init(RaftTimer raftTimer){
        this.schedule(raftTimer, new Random().nextInt(150)+150,TimeUnit.MILLISECONDS);
    }


    //Executor的必须继承的run方法，定时器为单线程，该方法是定时器线程的loop方法
    @Override
    protected void run() {
        while (!closed){
            this.runAllTasks();
        }
    }

}
