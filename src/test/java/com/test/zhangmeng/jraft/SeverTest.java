package com.test.zhangmeng.jraft;

import com.test.zhangmeng.entry.RaftEntryLogUseList;
import com.test.zhangmeng.meta.ClientRpcReq;
import com.test.zhangmeng.protocol.RaftLeader;
import com.test.zhangmeng.protocol.RaftRegister;
import com.test.zhangmeng.protocol.RaftServerContext;
import com.test.zhangmeng.server.ClientChannelInitilizer;
import com.test.zhangmeng.server.NettyClientLoop;
import com.test.zhangmeng.server.NettyServerLoop;
import com.test.zhangmeng.server.ServerChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.junit.Test;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class SeverTest {

    @Test
    public void testServer() throws InterruptedException {
        NettyClientLoop nettyClientLoop = new NettyClientLoop(new ClientChannelInitilizer());
        SocketChannel socketChannel = nettyClientLoop.connServer("127.0.0.1", 22224);
        ClientRpcReq<Long> clientRpcReq = new ClientRpcReq<Long>();
        clientRpcReq.setApplyOrder(12L);
        socketChannel.writeAndFlush(clientRpcReq);
        synchronized (this) {
            this.wait();
        }
    }

    @Test
    public void testServerRegister(){
        RaftLeader raftLeader=new RaftLeader(new RaftEntryLogUseList());
        RaftServerContext raftServerContext=new RaftServerContext();
        raftServerContext.setRaftServer(raftLeader);
        NettyServerLoop nettyServerLoop = new NettyServerLoop(new ServerChannelInitializer(raftServerContext),22222);
        nettyServerLoop.startServerLoop();

    }

    @Test
    public void justTest() {
        RaftRegister raftRegister = new RaftRegister();
        System.out.println(Integer.toBinaryString(raftRegister.getRegister()));
        raftRegister.setting(5);
        raftRegister.setting(4);
        System.out.println(Integer.toBinaryString(raftRegister.getRegister()));
        System.out.println(raftRegister.getStation(5));
        raftRegister.resetting(5);
        System.out.println(Integer.toBinaryString(raftRegister.getRegister()));
        System.out.println(raftRegister.getStation(4));
        System.out.println(raftRegister.getStation(5));

    }

}
