package com.netease.cloudmusic.entry;

/**
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public class RaftEntry<T> implements AbstractEntry<T> {

    private final long term;

    private final long index;

    RaftEntry<T> next;

    RaftEntry<T> before;

    T applyOrder;

    RaftEntry(long term,long index,T applyOrder,RaftEntry<T> before,RaftEntry<T> next){
        this.term=term;
        this.index=index;
        this.applyOrder=applyOrder;
        this.before=before;
        this.next=next;

    }

    public T getApplyOrder() {
        return applyOrder;
    }

    public T setApplyOrder(T applyOrder) {
        this.applyOrder=applyOrder;
        return applyOrder;
    }

    public long getTerm() {
        return term;
    }

    public long getIndex() {
        return index;
    }
}
