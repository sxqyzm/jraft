package com.test.zhangmeng.meta;

import java.io.Serializable;

/**
 * Created by hzzhangmeng2 on 2017/2/12.
 */
public class ClientRpcResp implements Serializable {
    private static final long serialVersionUID = -8694787915871550697L;
    private int code;
    private boolean succ;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
