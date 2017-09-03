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

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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
    public void justTest() throws IOException {
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


        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        Socket server = new Socket(InetAddress.getLocalHost(), 5678);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
        PrintWriter out = new PrintWriter(server.getOutputStream());
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = wt.readLine();
            out.println(str);
            out.flush();
            if (str.equals("end")) {
                break;
            }
            System.out.println(in.readLine());
        }
        server.close();

    }

}
