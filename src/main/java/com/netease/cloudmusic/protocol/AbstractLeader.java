package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.meta.ClientRpcReq;

/**
 * raft协议中leader角色行为定义接口
 * Created by zhangmeng on 2017/3/12.
 */
public interface AbstractLeader {

    /**
     * 处理客户端状态请求
     * @param clientRpcReq
     * @return
     */
    boolean proceeClientReq(ClientRpcReq clientRpcReq);

    /**
     * 给follower进行heartBeat，防止其进入选举状态
     * @return
     */
    boolean doHeartBeat();



    boolean processAppenResp(AppRpcResp appRpcResp);

}
