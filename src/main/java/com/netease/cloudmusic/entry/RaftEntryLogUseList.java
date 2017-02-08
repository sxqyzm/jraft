package com.netease.cloudmusic.entry;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * entryLog 的双向链表实现
 * ******** 《--  ********* 《--  *********
 * * head *       * entry *       * tail *
 * ******** --》  ********* --》  *********
 * Created by hzzhangmeng2 on 2017/2/8.
 */
public class RaftEntryLogUseList<T> implements AbstractEntryLog<T> {

    private AbstractEntry<T> head;
    private AbstractEntry<T> tail;
    private AtomicLong length;
    private Lock lock=new ReentrantLock();

    public RaftEntryLogUseList(){
        head=new RaftEntry<T>(0,0,null,null,null);
        tail=new RaftEntry<T>(0,0,null,null,null);
        head.setNext(tail);
        tail.setBefore(head);
        length=new AtomicLong(0);
    }

    public long getLogLen() {
        return length.get();
    }

    public AbstractEntry<T> getEntryByIndex(long index) {
        if (index<=0)return head;
        AbstractEntry<T> entryIndex=head;
        while (entryIndex.next().next()!=null){
            entryIndex=entryIndex.next();
            if (entryIndex.getIndex()==index)return entryIndex;
        }
        return head;
    }

    public boolean appendEntry(AbstractEntry<T> entry) {

        if (entry==null)return false;
        lock.lock();
        try {
        AbstractEntry<T> entryIndex=tail.before();
        tail.setBefore(entry);
        entryIndex.setNext(entry);
        entry.setNext(tail);
        entry.setBefore(entryIndex);
        length.incrementAndGet();
        }finally {
            lock.unlock();
        }
        return true;
    }

    public boolean deleteBackEntrys(AbstractEntry<T> entry) {
        if (entry==null||entry.next()==null)return true;
        lock.lock();
        try {
            entry.setNext(tail);
            tail.setBefore(entry);
        }finally {
            lock.unlock();
        }
        return true;
    }
}
