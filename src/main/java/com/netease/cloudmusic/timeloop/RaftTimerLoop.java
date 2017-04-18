package com.netease.cloudmusic.timeloop;

import com.netease.cloudmusic.protocol.RaftServerContext;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.Random;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * raft协议中的定时器实现，基于netty的EventExecutor
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public  class RaftTimerLoop extends SingleThreadEventExecutor {

    //定时器停止开关
    private volatile boolean closed=false;

    private RaftServerContext raftServerContext;

    public RaftTimerLoop(RaftServerContext raftServerContext) {
        super(new DefaultEventExecutorGroup(1), new DefaultThreadFactory("timerloop"), true);
        this.raftServerContext=raftServerContext;
    }
    public RaftTimerLoop(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp){
        super(parent,threadFactory,addTaskWakesUp);
    }

    //初始化时添加一个定时任务
    public void init(){
        //RaftTimer raftTimer=new RaftTimer(this);
        RaftLeaderTimer raftLeaderTimer=new RaftLeaderTimer(this);
        //this.schedule(raftTimer, new Random().nextInt(150)+150,TimeUnit.MILLISECONDS);
        this.schedule(raftLeaderTimer, new Random().nextInt(50)+90,TimeUnit.MILLISECONDS);
    }


    //Executor的必须继承的run方法，定时器为单线程，该方法是定时器线程的loop方法
    @Override
    protected void run() {
        while (!closed){
            this.runAllTasks();
        }
    }

    public RaftServerContext getRaftServerContext() {
        return raftServerContext;
    }

    public void setRaftServerContext(RaftServerContext raftServerContext) {
        this.raftServerContext = raftServerContext;
    }
}
