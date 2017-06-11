package com.test.zhangmeng.server;

import com.test.zhangmeng.protocol.RaftServerContext;
import com.netease.cloudmusic.server.inputHandler.*;
import com.test.zhangmeng.server.inputHandler.*;
import com.test.zhangmeng.server.outputHandler.ServerEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by hzzhangmeng2 on 2017/1/20.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RaftServerContext raftServerContext;

    public ServerChannelInitializer(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ServerEncoder());
        socketChannel.pipeline().addLast(new ServerDecoder());
        socketChannel.pipeline().addLast(new RaftAppendReqHandler(raftServerContext));
        socketChannel.pipeline().addLast(new RaftAppendRespHanler(raftServerContext));
        socketChannel.pipeline().addLast(new RaftClientReqHandler(raftServerContext));
        socketChannel.pipeline().addLast(new RaftInitReqHandler(raftServerContext));
        socketChannel.pipeline().addLast(new RaftVoteReqHandler(raftServerContext));
        socketChannel.pipeline().addLast(new RaftVoteRespHandler(raftServerContext));
    }
}
