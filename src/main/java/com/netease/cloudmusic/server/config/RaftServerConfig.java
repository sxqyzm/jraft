package com.netease.cloudmusic.server.config;

import com.netease.cloudmusic.enums.RoleEnum;
import com.netease.cloudmusic.server.bootstrap.HostAndPort;

import java.util.List;
import java.util.Map;

/**
 * raft服务配置类
 * Created by hzzhangmeng2 on 2017/3/27.
 */
public class RaftServerConfig {
    private final RoleEnum roleEnum;

    private final HostAndPort currentServer;

    private final List<Long> nodeId;
    private final List<HostAndPort> servers;

    public RaftServerConfig(RoleEnum roleEnum, HostAndPort currentServer, List<Long> nodeId, List<HostAndPort> servers) {
        this.roleEnum = roleEnum;
        this.currentServer = currentServer;
        this.nodeId = nodeId;
        this.servers = servers;
    }

    public HostAndPort getCurrentServer() {
        return currentServer;
    }

    public List<HostAndPort> getServers() {
        return servers;
    }

    public List<Long> getNodeId() {
        return nodeId;
    }
}
