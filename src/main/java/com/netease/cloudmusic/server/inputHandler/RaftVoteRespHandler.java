package com.netease.cloudmusic.server.inputHandler;

import com.netease.cloudmusic.meta.VoteRpcResp;
import com.netease.cloudmusic.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理vote响应的handler
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftVoteRespHandler extends SimpleChannelInboundHandler<VoteRpcResp> {

    private RaftServerContext raftServerContext;

    public RaftVoteRespHandler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, VoteRpcResp voteRpcResp) throws Exception {
        raftServerContext.getRaftServer().processVoteResp(voteRpcResp);
    }
}
