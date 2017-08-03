package com.home.wanyu.mEmeu;

/**
 * Created by wanyu on 2017/7/21.
 */
//门锁状态
public enum LockState {
    LOCKED,UNLOCK;//已锁，未锁
    String[]sta={"已锁","已开","状态未知"};
    public String getLockState(LockState state){
        switch (state){
            case LOCKED:
                return sta[0];
            case UNLOCK:
                return sta[1];
        }
        return sta[2];
    }

}
