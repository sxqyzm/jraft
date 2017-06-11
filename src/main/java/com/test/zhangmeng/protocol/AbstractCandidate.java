package com.test.zhangmeng.protocol;

import com.test.zhangmeng.meta.VoteRpcResp;

/**
 *raft协议中candidate角色行为定义，candidate具有发起选举的行为
 * Created by hzzhangmeng2 on 2017/3/7.
 */
public interface AbstractCandidate {
    /**
     * 发起选举
     */
    void startVote();

    /**
     * 终止选举
     */
    void finishVote();

    boolean processVoteResp(VoteRpcResp voteRpcResp);

}
