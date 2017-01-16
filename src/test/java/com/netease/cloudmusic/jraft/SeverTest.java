package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.Server.AbstractRaftServer;
import org.junit.Test;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class SeverTest {

    @Test
    public void testServer(){
        AbstractRaftServer abstractRaftServer=new AbstractRaftServer();
        abstractRaftServer.start();
    }

}
