package com.netease.cloudmusic.entry;

/**
 *raft 状态命令entry行为定义
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public interface AbstractEntry<T> {

    T getApplyOrder();

    T setApplyOrder(T applyOrder);

}
