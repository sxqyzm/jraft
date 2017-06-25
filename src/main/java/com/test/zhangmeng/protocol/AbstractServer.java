package com.test.zhangmeng.protocol;

import com.test.zhangmeng.entry.AbstractEntry;
import com.test.zhangmeng.meta.ClientRpcReq;
import com.test.zhangmeng.meta.ClientRpcResp;

/**
 * raft 节点server行为定义，follower、candidate、leader都是server
 * raft是一个用于复制状态机的一致性协议，所以server的基本功能就是对状态命令（entry or log）的管理,server可以看做是没有raft协议的单节点stateMachine所具有的行为。
 * AbstractServer定义了以下行为
 *      1：接收客户端命令请求
 *      2：得到已经持久化到该server的最后一个entry记录
 *      3：已经被状态机执行的最后一个entry记录
 *
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public interface AbstractServer<T> {

    /**
     * 接收客户端命令请求
     * @return
     */
    ClientRpcResp appendEntry(ClientRpcReq<T> clientRpcReq);

    /**
     * 得到已经持久化到该server的最后一个entry记录
     * @return
     */
    AbstractEntry<T> getCommitIndex();

    /**
     * 已经被状态机执行的最后一个entry记录
     * @return
     */
    AbstractEntry<T> getApplyId();

}
