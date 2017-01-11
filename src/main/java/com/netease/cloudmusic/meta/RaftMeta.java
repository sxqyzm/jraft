package com.netease.cloudmusic.meta;

import java.io.Serializable;

/**
 * Created by hzzhangmeng2 on 2017/1/9.
 */
public class RaftMeta implements Serializable {
    private long term;
    private LogEntry logEntry;
}
