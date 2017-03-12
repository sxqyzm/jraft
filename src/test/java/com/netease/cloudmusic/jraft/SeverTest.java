package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.server.RaftNetWork;
import org.junit.Test;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class SeverTest {

    @Test
    public void testServer(){
        RaftNetWork abstractRaftServer=new RaftNetWork();
        abstractRaftServer.start();
    }

}
