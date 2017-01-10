package com.netease.cloudmusic.role;

import com.netease.cloudmusic.enums.RoleResponse;
import com.netease.cloudmusic.meta.RaftMeta;

/**
 * Created by hzzhangmeng2 on 2017/1/9.
 */

/**
 *server basic method
 */
public interface RoleAction {
    RoleResponse recMsg(RaftMeta raftMeta);
    RoleResponse sendMsg(RaftMeta raftMeta);
    RoleResponse applyMsg(RaftMeta raftMeta);
}
