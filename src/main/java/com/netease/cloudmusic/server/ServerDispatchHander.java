package com.netease.cloudmusic.server;

import com.netease.cloudmusic.meta.ReqBasicMeta;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public class ServerDispatchHander extends SimpleChannelInboundHandler<ReqBasicMeta> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ReqBasicMeta reqBasicMeta) throws Exception {

    }
}
