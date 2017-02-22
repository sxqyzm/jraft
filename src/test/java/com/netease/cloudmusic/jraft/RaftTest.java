package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.protocol.AbstractRaftProtocol;
import com.netease.cloudmusic.timeloop.RaftTimer;
import com.netease.cloudmusic.timeloop.RaftTimerLoop;
import org.junit.Test;


/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTest {

    @Test
    public void loopTest(){
        RaftTimerLoop timeLoop=new RaftTimerLoop();
        RaftTimer raftTimer=new RaftTimer(timeLoop,new AbstractRaftProtocol());
        timeLoop.init(raftTimer);
        System.out.println("add"+System.currentTimeMillis());
        while (true){

        }
    }


}
