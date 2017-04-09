package com.netease.cloudmusic.server.inputHandler;

import com.netease.cloudmusic.meta.InitFollwerReq;
import com.netease.cloudmusic.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhangmeng on 2017/4/9.
 */
public class RaftInitReqHandler extends SimpleChannelInboundHandler<InitFollwerReq> {

    private RaftServerContext raftServerContext;

    public RaftInitReqHandler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, InitFollwerReq initFollwerReq) throws Exception {
        if (!raftServerContext.getRaftServer().isInitedFollwer()){
            raftServerContext.getRaftServer().convertToFollower(initFollwerReq.getLeaderId(),initFollwerReq.getLeaderTerm(),raftServerContext);
        }
        System.out.println("follower started");
    }
}