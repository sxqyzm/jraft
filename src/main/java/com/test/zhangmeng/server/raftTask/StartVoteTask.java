package com.test.zhangmeng.server.raftTask;

import com.test.zhangmeng.protocol.RaftProtocol;
import com.test.zhangmeng.protocol.RaftServerContext;

/**
 * 选举发起任务实现
 * Created by hzzhangmeng2 on 2017/3/24.
 */
public class StartVoteTask implements Runnable {
    private RaftServerContext raftServerContext;
    public void run() {
        if (RaftProtocol.startVote(raftServerContext.getRaftServer())){
            //TODO 发起选举成功后等待响应
        }
        //发起失败则下次再试
    }
}
