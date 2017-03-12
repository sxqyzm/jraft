package com.netease.cloudmusic.protocol;

import java.util.Map;

/**
 * raft系统配置信息
 * Created by zhangmeng on 2017/3/12.
 */
public class RaftSystemState {
    /*raft系统中的节点数目，根据配置文件读取*/
  public static int nodeNum;
    /*raft系统中节点id集合*/
  public static long[] nodeIds;
    /*raft系统中节点ip集合*/
  public static Map<Long,String> nodeIps;
    /*raft系统中节点端口集合*/
  public static Map<Long,Integer> nodePorts;
}
