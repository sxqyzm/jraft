package com.netease.cloudmusic.jraft;

import com.netease.cloudmusic.meta.ClientRpcReq;
import com.netease.cloudmusic.server.NettyClientLoop;
import com.netease.cloudmusic.server.RaftNetWork;
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

}
