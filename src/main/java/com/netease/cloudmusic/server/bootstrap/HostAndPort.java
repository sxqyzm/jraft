package com.netease.cloudmusic.server.bootstrap;

/**
 * Created by hzzhangmeng2 on 2017/3/29.
 */
public class HostAndPort {
    private String host;
    private int port;

    public HostAndPort(String host ,int port){
        this.host=host;
        this.port=port;
    }

    public int hashCode() {
        String hostAndPort=host+port;
        return hostAndPort.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HostAndPort))return false;
        HostAndPort hostAndPort=(HostAndPort)obj;
        if (host.equalsIgnoreCase(hostAndPort.host)&&port==hostAndPort.port)return true;
        return false;
    }

    public String getHost(){
        return host;
    }

    public int getPort(){
        return port;
    }


}
