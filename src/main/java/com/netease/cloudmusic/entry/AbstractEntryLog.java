package com.netease.cloudmusic.entry;

/**
 *状态序列命令定义
 * Created by hzzhangmeng2 on 2017/2/7.
 */
public interface AbstractEntryLog<T> {

    /**
     * 得到序列长度
     * @return  length of log
     */
    long getLogLen();

    /**
     * 根据位置index获取到对应的entry对象
     * @param index
     * @return the entry object
     */
    AbstractEntry<T> getEntryByIndex(long index);

    /**
     * 添加一个entry到序列尾部
     * @param entry
     * @return the result of apeending entry to log
     */
    boolean appendEntry(AbstractEntry<T> entry);


    /**
     * 将当前entry后的entry删除掉
     * @param entry
     * @return
     */
    boolean deleteBackEntrys(AbstractEntry<T> entry);


}
