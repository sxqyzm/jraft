package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.meta.*;

/**
 * raft 节点server行为定义，follower、candidate、leader都是server
 * raft是一个用于复制状态机的一致性协议，所以server的基本功能就是对状态命令（entry or log）的管理,server可以看做是没有raft协议的单节点stateMachine所具有的行为。
 * AbstractServer定义了以下行为
 *      1：接收客户端命令请求
 *      2：得到server当前term
 *      4：得到server对应的状态序列log
 *      5：得到已经持久化到该server的最后一个entry记录
 *      6：已经被状态机执行的最后一个entry记录
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
     * 得到server当前周期
     * @return
     */
    long getCurrentTerm();

    /**
     * 得到server对应的状态序列log
     * @return
     */
    AbstractEntryLog<T> getEntryLog();

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
