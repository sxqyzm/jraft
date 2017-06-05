package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.VoteRpcReq;
import com.test.zhangmeng.meta.VoteRpcResp;
import com.test.zhangmeng.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理vote请求的handler
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftVoteReqHandler extends SimpleChannelInboundHandler<VoteRpcReq> {

    private RaftServerContext raftServerContext;

    public RaftVoteReqHandler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    protected void channelRead0(ChannelHandlerContext ctx, VoteRpcReq voteRpcReq) throws Exception {
        VoteRpcResp voteRpcResp=this.raftServerContext.getRaftServer().acceptVoteRpc(voteRpcReq);
        ctx.writeAndFlush(voteRpcResp);
    }
}
