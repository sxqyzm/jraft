package com.netease.cloudmusic.protocol;

/**
 * Created by hzzhangmeng2 on 2017/2/6.
 */

import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.meta.VoteRpcResp;

/**
 * raft中follower行为定义，follower是基础状态，无论是leader还是candidate，都是从follower转换拓展而来
 *follower 相比于SingleNodeServer，多了对raft协议的响应，包括：回应选举，回应心跳，回应添加entry
 */
public interface AbstractFollower<T> {
    /**
     * 接收raft协议中的选举信息
     *
     */
    VoteRpcResp acceptVoteRpc(VoteRpcReq voteRpcReq);

    /**
     * 接收raft协议中的心跳以及添加log信息
     */
    AppRpcResp acceptAppenRpc(AppRpcReq<T> appRpcReq);

}
