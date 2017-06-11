package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.AppRpcReq;
import com.test.zhangmeng.meta.AppRpcResp;
import com.test.zhangmeng.protocol.RaftServerContext;
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
