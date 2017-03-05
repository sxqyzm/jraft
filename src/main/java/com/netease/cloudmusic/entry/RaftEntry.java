package com.netease.cloudmusic.entry;

/**
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public class RaftEntry<T> implements AbstractEntry<T> {

    private  long term;

    private  long index;

    private AbstractEntry<T> next;

    private AbstractEntry<T> before;

    T applyOrder;

    public RaftEntry(T applyOrder){
        this(applyOrder,null,null);
    }
    public RaftEntry(T applyOrder,RaftEntry<T> before,RaftEntry<T> next){
        this(0,0,applyOrder,before,next);
    }

    public RaftEntry(long term,long index,T applyOrder,RaftEntry<T> before,RaftEntry<T> next){
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

    public AbstractEntry<T> next() {
        return next;
    }

    public AbstractEntry<T> before() {
        return before;
    }

    public AbstractEntry<T> setNext(AbstractEntry<T> entry) {
        next=entry;
        return entry;
    }

    public AbstractEntry<T> setBefore(AbstractEntry<T> entry) {
        before=entry;
        return entry;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public void setIndex(long index) {
        this.index = index;
    }
}
