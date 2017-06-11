package com.test.zhangmeng.server.inputHandler;

import com.test.zhangmeng.meta.ClientRpcReq;
import com.test.zhangmeng.protocol.RaftServerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理client发来的日志append请求的handler
 * Created by hzzhangmeng2 on 2017/3/23.
 */
public class RaftClientReqHandler extends SimpleChannelInboundHandler<ClientRpcReq> {

    private RaftServerContext raftServerContext;

    public RaftClientReqHandler(RaftServerContext raftServerContext){
        this.raftServerContext=raftServerContext;
    }


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientRpcReq clientRpcReq) throws Exception {
            raftServerContext.getRaftServer().proceeClientReq(clientRpcReq);
    }
}
