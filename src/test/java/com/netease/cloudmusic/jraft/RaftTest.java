package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.entry.RaftEntryLogUseList;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.protocol.RaftLeader;
import com.netease.cloudmusic.protocol.RaftServerContext;
import com.netease.cloudmusic.server.bootstrap.HostAndPort;
import com.netease.cloudmusic.server.bootstrap.RaftServerBootstrap;
import com.netease.cloudmusic.server.config.RaftServerConfig;
import com.netease.cloudmusic.timeloop.RaftTimer;
import com.netease.cloudmusic.timeloop.RaftTimerLoop;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class RaftTest {

    @Test
    public void loopTest(){
        RaftLeader raftLeader=new RaftLeader(new RaftEntryLogUseList());
        RaftServerContext raftServerContext=new RaftServerContext();
        raftServerContext.setRaftServer(raftLeader);
        RaftTimerLoop timeLoop=new RaftTimerLoop(raftServerContext);
        RaftTimer raftTimer=new RaftTimer(timeLoop);
        timeLoop.init(raftTimer);
        System.out.println("add"+System.currentTimeMillis());
        while (true){

        }
    }


    @Test
    public void mainTest() throws InterruptedException {
        HostAndPort hostAndPort_1=new HostAndPort("127.0.0.1",22224);
        HostAndPort hostAndPort_2=new HostAndPort("127.0.0.1",22222);
        HostAndPort hostAndPort_3=new HostAndPort("127.0.0.1",22223);
        List<HostAndPort> hostAndPortList=new ArrayList<HostAndPort>();
        hostAndPortList.add(hostAndPort_1);
        hostAndPortList.add(hostAndPort_2);
        hostAndPortList.add(hostAndPort_3);

        RaftServerConfig raftServerConfig=new RaftServerConfig(RoleEnum.FOLLOWER, hostAndPort_1,hostAndPortList);
        RaftServerContext raftServerContext=new RaftServerContext();
        raftServerContext.setRaftServer(new RaftLeader(new RaftEntryLogUseList()));

        RaftServerBootstrap raftServerBootstrap=new RaftServerBootstrap(raftServerConfig,raftServerContext);
        raftServerBootstrap.start(RoleEnum.LEADER);
        while (true){
        }

    }
}
