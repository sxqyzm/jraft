package com.test.zhangmeng.meta;

import java.io.Serializable;

/**
 * response of AppRpcReq
 * Created by hzzhangmeng2 on 2017/2/8.
 */
public class AppRpcResp implements Serializable {
    private static final long serialVersionUID = -7993580987559255563L;

    private long nodeId;
    private long term;
    private boolean success;

    public AppRpcResp(long nodeId,long currentTerm,boolean result){
        this.nodeId=nodeId;
        this.term=currentTerm;
        this.success=result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }
}
