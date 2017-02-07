package com.netease.cloudmusic.Protocol;

/**
 * raft 节点server行为定义，follower、candidate、leader都是server
 * raft是一个用于复制状态机的一致性协议，所以server的基本功能就是对状态命令（entry or log）的管理
 * AbstractServer定义了以下行为
 *      1：append一个entry到状态命令序列中
 *      2：根据index得到entry信息
 *      3：查询序列长度
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public interface AbstractServer {

    long appendEntry();

    void getEntry(long index);

    long getEntryLen();

}
