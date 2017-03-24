package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.entry.RaftEntryLogUseList;
import com.netease.cloudmusic.protocol.RaftLeader;
import com.netease.cloudmusic.protocol.RaftServerContext;
import com.netease.cloudmusic.timeloop.RaftTimer;
import com.netease.cloudmusic.timeloop.RaftTimerLoop;
import org.junit.Test;


/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTest {

    @Test
    public void loopTest(){
        RaftLeader raftLeader=new RaftLeader(22222,new RaftEntryLogUseList());
        RaftServerContext raftServerContext=new RaftServerContext();
        raftServerContext.setRaftServer(raftLeader);
        RaftTimerLoop timeLoop=new RaftTimerLoop(raftServerContext);
        RaftTimer raftTimer=new RaftTimer(timeLoop);
        timeLoop.init(raftTimer);
        System.out.println("add"+System.currentTimeMillis());
        while (true){

        }
    }
}
