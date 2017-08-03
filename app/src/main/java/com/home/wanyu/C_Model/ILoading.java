package com.home.wanyu.C_Model;

/**
 * Created by wanyu on 2017/7/27.
 */

public interface ILoading {
    void onLoading();//开始请求
    void onStopLoading();//请求结束
    void onNetworkError(String interfaceName);//网络异常（无网络／服务器请求失败）参数：接口名称
    void onGsonError(String gsonStr);//服务器返回数据异常，json无法转化
}
