package com.netease.cloudmusic.server.config;

import com.netease.cloudmusic.server.bootstrap.HostAndPort;

import java.util.List;
import java.util.Map;

/**
 * raft服务配置类
 * Created by hzzhangmeng2 on 2017/3/27.
 */
public class RaftServerConfig {

    private final HostAndPort currentServer;

    private final List<HostAndPort> servers;

    public RaftServerConfig(HostAndPort currentServer, List<HostAndPort> servers) {
        this.currentServer = currentServer;
        this.servers = servers;
    }

    public HostAndPort getCurrentServer() {
        return currentServer;
    }

    public List<HostAndPort> getServers() {
        return servers;
    }
}
