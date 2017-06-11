package com.test.zhangmeng.jraft;

<<<<<<< HEAD:src/test/java/com/netease/cloudmusic/jraft/SeverTest.java
import com.netease.cloudmusic.meta.ClientRpcReq;
import com.netease.cloudmusic.protocol.RaftRegister;
import com.netease.cloudmusic.server.NettyClientLoop;
import com.netease.cloudmusic.server.RaftNetWork;
=======
import com.test.zhangmeng.meta.ClientRpcReq;
import com.test.zhangmeng.server.NettyClientLoop;
>>>>>>> 9f410a59c185e13f88f57dc2edfdcdaef48f118a:src/test/java/com/test/zhangmeng/jraft/SeverTest.java
import io.netty.channel.socket.SocketChannel;
import org.junit.Test;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class SeverTest {

    @Test
    public void testServer() throws InterruptedException {
        NettyClientLoop nettyClientLoop=new NettyClientLoop();
        SocketChannel socketChannel=nettyClientLoop.connServer("127.0.0.1", 22224);
        ClientRpcReq<Long> clientRpcReq=new ClientRpcReq<Long>();
        clientRpcReq.setApplyOrder(12L);
        socketChannel.writeAndFlush(clientRpcReq);
        synchronized (this){
            this.wait();
        }
    }

    @Test
    public void justTest(){
        RaftRegister raftRegister=new RaftRegister();
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
