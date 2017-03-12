package com.netease.cloudmusic.protocol;

import com.netease.cloudmusic.entry.AbstractEntry;
import com.netease.cloudmusic.entry.AbstractEntryLog;
import com.netease.cloudmusic.meta.AppRpcReq;
import com.netease.cloudmusic.meta.AppRpcResp;
import com.netease.cloudmusic.meta.VoteRpcReq;
import com.netease.cloudmusic.meta.VoteRpcResp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Raft Follower实现
 * Created by zhangmeng on 2017/2/26.
 */
public class RaftFollwer<T> extends SingleNodeServer<T> implements AbstractFollower<T> {

    private RaftProtocol raftProtocol;

    public  RaftFollwer(int port,AbstractEntryLog<T> abstractEntryLog)
    {
        super(port,abstractEntryLog);
        raftProtocol=new RaftProtocol();
    }

    @Override
    public VoteRpcResp acceptVoteRpc(VoteRpcReq voteRpcReq) {
        try {
            RaftServerState.stateLcok.lock();
            boolean grantVote = raftProtocol.processVoteRequest(voteRpcReq,this);
            return new VoteRpcResp(currentTerm, grantVote);
        }finally {
            RaftServerState.stateLcok.unlock();
        }
    }

    @Override
    public AppRpcResp acceptAppenRpc(AppRpcReq<T> appRpcReq) {
        try {
            RaftServerState.stateLcok.lock();
            boolean grantAppend = false;
            grantAppend = raftProtocol.processAppenRequest(appRpcReq,this);
            return new AppRpcResp(currentTerm, grantAppend);
        }finally {
            RaftServerState.stateLcok.unlock();
        }
    }

    /**
     * 1. Reply false if term < currentTerm (§5.1)
     * 2. Reply false if log doesn’t contain an entry at prevLogIndex
     whose term matches prevLogTerm (§5.3)
     * 3. If an existing entry conflicts with a new one (same index
     but different terms), delete the existing entry and all that
     follow it (§5.3)
     * 4. Append any new entries not already in the log
     * 5. If leaderCommit > commitIndex, set commitIndex =
     min(leaderCommit, index of last new entry)
     */
    public boolean processApendRpc(AppRpcReq<T> appRpcReq){
        //conditon 1
        if (currentTerm<=appRpcReq.getLeaderTerm()){
         AbstractEntry<T> preEntry=getEntryLog().getEntryByIndex(appRpcReq.getPrevLogIndex());
            //conditon 2
            if (preEntry!=null&&preEntry.getTerm()==appRpcReq.getLeaderTerm()){
                //有待添加的entry
                AbstractEntry<T> entryList[]=appRpcReq.getAppendEntrys();
                if (entryList!=null&&entryList.length>0){
                    int startApeend=0;
                        //遍历跳过已填加的entry或者去除follower存在conflict的entry
                    while (preEntry.next().next()!=null&&startApeend<entryList.length){
                        if (preEntry.next().getTerm()!=entryList[startApeend].getTerm()){
                            getEntryLog().deleteBackEntrys(preEntry.next());
                        }else{
                            preEntry=preEntry.next();
                            startApeend++;
                        }
                    }
                    //preEntry是最后一个entry，直接将待添加的entry[]加到preEntry后面
                    for (int i=startApeend;i<entryList.length;i++){
                        getEntryLog().appendEntry(entryList[i]);
                    }
                    }
                updateAfterAppend(appRpcReq);
                return true;
            }
        }
        return false;
    }
}
