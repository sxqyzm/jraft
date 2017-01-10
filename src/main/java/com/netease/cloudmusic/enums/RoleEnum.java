package com.netease.cloudmusic.enums;

/**
 * Created by hzzhangmeng2 on 2017/1/9.
 */
public enum RoleEnum {

    FOLLOWER(0,"follower"),
    CANDIDATE(1,"candidate"),
    LEADER(2,"leader");

    private RoleEnum(int type,String name){
        this.type=type;
        this.name=name;
    }
    int type;
    String name;
}
