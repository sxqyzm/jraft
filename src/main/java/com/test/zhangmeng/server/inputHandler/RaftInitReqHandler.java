package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.InitFollwerReq;
import com.test.zhangmeng.protocol.RaftFollwer;
import com.test.zhangmeng.protocol.RaftServerContext;
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
        RaftFollwer raftFollwer=raftServerContext.getRaftServer();

        if (!raftFollwer.isInitedFollwer()){
            raftFollwer.getRaftNetWork().initConnNodes();
            raftFollwer.convertToFollower(initFollwerReq.getLeaderId(),initFollwerReq.getLeaderTerm(),raftServerContext);
        }
        System.out.println("follower started");
    }
}