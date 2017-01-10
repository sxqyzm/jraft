package com.netease.cloudmusic.enums;

/**
 * Created by hzzhangmeng2 on 2017/1/9.
 */
public enum RoleResponse {

    REJECT(0,"reject"),
    SUCCESS(1,"success");

    private RoleResponse(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    int code;
    String msg;
}
