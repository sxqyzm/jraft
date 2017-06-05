package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.AppRpcResp;
import com.test.zhangmeng.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理append响应的handler
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftAppendRespHanler extends SimpleChannelInboundHandler<AppRpcResp> {

    private RaftServerContext raftServerContext;

    public RaftAppendRespHanler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AppRpcResp appRpcResp) throws Exception {
        raftServerContext.getRaftServer().processAppenResp(appRpcResp);

        //TODO,处理完append响应后后续操作
    }
}
