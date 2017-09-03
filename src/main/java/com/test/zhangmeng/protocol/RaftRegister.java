package com.test.zhangmeng.protocol;

/**
 * Created by zhangmeng on 2017/6/11.
 * raft协议节点交互状态信息类
 * 定义状态寄存器
 * 定义统一操作方法
 */
public class RaftRegister {
    private int register = 0x00000000;

    /**
     * 模拟寄存器指定station置位
     *
     * @param station
     * @return
     */
    public boolean setting(int station) {
        if (station >= 0 && station <= 7) {
            register |= getTwoExp(station - 1);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 得到模拟寄存器指定位置的状态值
     *
     * @param station
     * @return
     */
    public boolean getStation(int station) {
        if (station < 0 || station > 7) return false;
        int tempVal = getRegister();
        tempVal = tempVal & getTwoExp(station - 1);
        if (tempVal == 0) return false;
        return true;
    }

    /**
     * 模拟寄存器指定station复位
     *
     * @param station
     * @return
     */
    public boolean resetting(int station) {
        if (station >= 0 && station <= 7) {
            register &= ((getTwoExp(8) - 1) - getTwoExp(station - 1));
        } else {
            return false;
        }
        return true;
    }

    /**
     * 得到模拟寄存器当前值
     *
     * @return
     */
    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    private int getTwoExp(int station) {
        int inited = 2;
        if (station <= 0) return 1;
        if (station == 1) return inited;
        for (int i = 1; i < station; i++) {
            inited = inited * 2;
        }
        return inited;
    }
}
