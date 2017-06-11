package com.test.zhangmeng.exception;

/**
 * Created by hzzhangmeng2 on 2017/3/31.
 */
public class RaftException extends RuntimeException {
    public RaftException(String message){
        super(message);
    }
}
