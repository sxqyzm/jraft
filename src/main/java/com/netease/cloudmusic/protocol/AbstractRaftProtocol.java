package com.netease.cloudmusic.protocol;

/**
 * Created by hzzhangmeng2 on 2017/1/11.
 */
public class AbstractRaftProtocol implements RaftProtocol {

    private int ServerRole;

    public void serverRoleHandle() {

    }

    public int getServerRole() {
        return ServerRole;
    }

    public void setServerRole(int serverRole) {
        ServerRole = serverRole;
    }
}
