package com.test.zhangmeng.protocol;

import com.test.zhangmeng.server.bootstrap.HostAndPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * raft系统配置信息
 * Created by zhangmeng on 2017/3/12.
 */
public class RaftSystemState {
    /*raft系统中的节点数目，根据配置文件读取*/
  public static int nodeNum;
  /*当前节点的node id*/
  public static long currentNodeId;
    /*raft系统中节点id集合*/
  public static List<Long> nodeIds=new ArrayList<Long>();
    /*raft系统中节点address集合*/
  public static Map<Long,HostAndPort> nodeHosts=new ConcurrentHashMap<Long, HostAndPort>();

  public static void init(long startId,int Num,HostAndPort hostAndPort){
    currentNodeId=startId;
    nodeNum=Num;
  }

  public static List<Long> getNodeIds(){
    return nodeIds;
  }

  public static Map<Long,HostAndPort> getNodeHosts(){
    return nodeHosts;
  }


}
