package com.test.zhangmeng.timeloop;

import com.test.zhangmeng.enums.RoleEnum;
import com.test.zhangmeng.protocol.RaftCandidate;
import com.test.zhangmeng.protocol.RaftProtocol;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangmeng on 2017/5/7.
 */
public class RaftCandidateTimer implements Runnable {
    private RaftTimerLoop raftTimerLoop;
    public RaftCandidateTimer(RaftTimerLoop raftTimerLoop){
        this.raftTimerLoop=raftTimerLoop;
    }
    public void run() {
        RaftCandidate raftCandidate=raftTimerLoop.getRaftServerContext().getRaftServer();
        if (raftCandidate.serverStat== RoleEnum.CANDIDATE) {
            raftCandidate.reStartCandidate();
            RaftProtocol.startVote(raftCandidate);
            raftTimerLoop.schedule(new RaftCandidateTimer(raftTimerLoop), new Random().nextInt(150) + 150, TimeUnit.MILLISECONDS);

        }
    }
}
