package com.test.zhangmeng.entry;

/**
 *raft 状态命令entry行为定义
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public interface AbstractEntry<T> {

    AbstractEntryLog<T> getEntryLog();

    AbstractEntryLog<T> setEntryLog(AbstractEntryLog<T> abstractEntryLog);

    T getApplyOrder();

    T setApplyOrder(T applyOrder);

    long getTerm();

    long getIndex();

    AbstractEntry<T> next();

    AbstractEntry<T> before();

    AbstractEntry<T> setNext(AbstractEntry<T> entry);

    AbstractEntry<T> setBefore(AbstractEntry<T> entry);

}
