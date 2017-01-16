package com.netease.cloudmusic.Server;

import io.netty.buffer.ByteBuf;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public interface RaftServer {
    void writeMsg(ByteBuf byteBuf);
    void readMsg(ByteBuf byteBuf);
}
