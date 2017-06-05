package com.test.zhangmeng.meta;

import java.io.Serializable;

/**
 * Created by hzzhangmeng2 on 2017/2/12.
 */
public class ClientRpcReq<T> implements Serializable {
    private static final long serialVersionUID = -7773133850307558296L;

    private T applyOrder;

    public T getApplyOrder() {
        return applyOrder;
    }

    public void setApplyOrder(T applyOrder) {
        this.applyOrder = applyOrder;
    }
}
