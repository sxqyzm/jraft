package com.netease.cloudmusic.meta;

import java.io.Serializable;

/**
 * Created by hzzhangmeng2 on 2017/1/9.
 */
public class RaftMeta implements Serializable {
    private int term;
    private LogEntry logEntry;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public LogEntry getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
    }
}
