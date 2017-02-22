package com.netease.cloudmusic.protocol;

/**
 * Created by hzzhangmeng2 on 2017/2/6.
 */

/**
 * raft中follower行为定义，follower是基础状态，无论是leader还是candidate，都是从follower转换拓展而来
 */
public interface AbstractFollower {

    /**
     * 1:
     */
    void acceptVoteRpc();

    void acceptHeartRpc();

    void acceptAppendRpc();

    void redirectClientRpc();

}
