package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.VoteRpcResp;
import com.test.zhangmeng.protocol.RaftServerContext;
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
        System.out.println("get vote resp:"+voteRpcResp.toString());
        raftServerContext.getRaftServer().processVoteResp(voteRpcResp);
    }
}
