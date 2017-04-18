package com.netease.cloudmusic.server.inputHandler;

import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * server间日志append请求处理handler
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftAppendReqHandler extends SimpleChannelInboundHandler<AppRpcReq> {

    private RaftServerContext raftServerContext;

    public RaftAppendReqHandler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }

    protected void channelRead0(ChannelHandlerContext ctx, AppRpcReq appRpcReq) throws Exception {
        System.out.println("get app Msg");
        AppRpcResp appRpcResp=raftServerContext.getRaftServer().acceptAppenRpc(appRpcReq);
        ctx.writeAndFlush(appRpcResp);
    }
}
