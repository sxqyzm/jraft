package com.netease.cloudmusic.server;

import java.util.Map;

/**
 * Created by hzzhangmeng2 on 2017/1/16.
 */
public interface ABstractRaftNet {

    /**
     * 建立到raft集群中其他server的连接
     * @param currentNodeId 节点id
     * @return 成功连接的数目
     */
    void connOtherServers(long currentNodeId);

    /**
     * 向raft网络中其他节点发送信息
     * @param obj
     */
    void writeMsg(Object obj);


    /**
     * 构建网络模型
     */
    void startNode();

}
