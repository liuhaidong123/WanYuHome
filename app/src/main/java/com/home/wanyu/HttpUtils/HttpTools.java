package com.home.wanyu.HttpUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.haveAddress.Root;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.Map;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class HttpTools {

    private FinalHttp mFinalHttp;
    private static HttpTools mHttpTools;
    private Gson mGson = new Gson();

    private HttpTools() {
        if (mFinalHttp == null) {
            mFinalHttp = new FinalHttp();
        }
    }

    //获取本类的实例对象，并且初始化FinalHttp类
    public static HttpTools getHttpToolsInstance() {
        if (mHttpTools == null) {
            //当初始化本类的时候，会初始化mFinalHttp
            mHttpTools = new HttpTools();
        }
        return mHttpTools;
    }

    //判断有没有用户的地址
    public void haveUserAddress(final Handler handler, String token) {
        String url = UrlTools.BASE + UrlTools.HAVE_USER_ADDRESS + "token=" + token;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "判断有没有用户的地址");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "判断有没有用户的地址" + s);
                try {
                    Message message = new Message();
                    Root root = mGson.fromJson(s, Root.class);
                    message.obj = root;
                    message.what = 100;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure：判断有没有用户的地址", strMsg);
                // handler.sendEmptyMessage(201);
            }
        });
    }

    //添加用户地址
    public void addUserAddress(final Handler handler, Map<String, String> map) {
        String url = UrlTools.BASE + UrlTools.ADD_USER_ADDRESS;
        mFinalHttp.post(url, new AjaxParams(map), new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart：添加用户地址", "");

            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess：添加用户地址", s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.addAddressNoOwner.Root root = mGson.fromJson(s, com.home.wanyu.bean.addAddressNoOwner.Root.class);
                    message.obj = root;
                    message.what = 101;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure：添加用户地址", strMsg);
            }
        });

    }

    //获取城市列表
    public void getCityList(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.GET_CITY_LIST;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取城市列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取城市列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.cityList.Root root = mGson.fromJson(s, com.home.wanyu.bean.cityList.Root.class);
                    message.obj = root;
                    message.what = 102;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure：获取城市列表", strMsg);
                // handler.sendEmptyMessage(201);
            }
        });
    }

    //获取城市编码获取小区列表
    public void getAreaList(final Handler handler, String AreaCode) {
        String url = UrlTools.BASE + UrlTools.GET_AREA_LIST + "AreaCode=" + AreaCode;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取小区列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取小区列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.areaList.Root root = mGson.fromJson(s, com.home.wanyu.bean.areaList.Root.class);
                    message.obj = root;
                    message.what = 103;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure：获取小区列表", strMsg);
                // handler.sendEmptyMessage(201);
            }
        });
    }

    //根据年份月份地址查询缴费状况
    public void getMoneyByYearMonthAddress(final Handler handler, String token, String address, String Yeartime, String monthtime) {
        String url = UrlTools.BASE + UrlTools.GET_USER_MONEY + "token=" + token + "&address=" + address + "&Yeartime=" + Yeartime + "&monthtime=" + monthtime;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "根据年份月份地址查询缴费状况");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "根据年份月份地址查询缴费状况" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.waterEleRan.Root root = mGson.fromJson(s, com.home.wanyu.bean.waterEleRan.Root.class);
                    message.obj = root;
                    message.what = 104;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "根据年份月份地址查询缴费状况" + strMsg);
                // handler.sendEmptyMessage(201);
            }
        });

    }


    //获得报修类型
    public void getRepairType(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.GET_REPAIR_TYPE;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获得报修类型");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获得报修类型" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.repairType.Root root = mGson.fromJson(s, com.home.wanyu.bean.repairType.Root.class);
                    message.obj = root;
                    message.what = 105;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "获得报修类型" + strMsg);
                // handler.sendEmptyMessage(201);
            }
        });

    }

    //提交报修类型
    public void submitRepairTypr(final Handler handler, AjaxParams ajaxParams) {

        String url = UrlTools.BASE + UrlTools.SUBMIT_REPAIR_TYPE;
        mFinalHttp.addHeader("Cookie","token="+ UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "提交报修类型");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "提交报修类型" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.repairSubmitType.Root root = mGson.fromJson(s, com.home.wanyu.bean.repairSubmitType.Root.class);
                    message.obj = root;
                    message.what = 106;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "提交报修类型" + strMsg);
                // handler.sendEmptyMessage(201);
            }
        });

    }
    //获得报修记录详情
    public void getRecordMsg(final Handler handler,String token,int state,int start,int limit) {
        String url = UrlTools.BASE + UrlTools.GET_RECORD_MSG+"token="+token+"&state="+state+"&start="+start+"&limit="+limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获得报修记录详情");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获得报修记录详情" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.RecordMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.RecordMsg.Root.class);
                    message.obj = root;
                    message.what = 107;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "获得报修记录详情" + strMsg);
                 handler.sendEmptyMessage(201);
            }
        });

    }
    //取消维修完成维修
    public void recordCancleFinish(final Handler handler,String token,int state,long id ) {
        String url = UrlTools.BASE + UrlTools.RRCORD_CANCLE_FINISH+"token="+token+"&state="+state+"&id="+id;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "取消维修完成维修");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "取消维修完成维修" + s);
                try {
                    Message message = new Message();
                   com.home.wanyu.bean.RecordCancleFinish.Root root = mGson.fromJson(s, com.home.wanyu.bean.RecordCancleFinish.Root.class);
                    message.obj = root;
                    message.what = 108;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "取消维修完成维修" + strMsg);
                //handler.sendEmptyMessage(201);
            }
        });

    }


    //友邻圈获取小区接口
    public void getCircleArea(final Handler handler,String token) {
        String url = UrlTools.BASE + UrlTools.GET_CIRCLE_AREA+"token="+token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "友邻圈获取小区接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "友邻圈获取小区接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleArea.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleArea.Root.class);
                    message.obj = root;
                    message.what = 110;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Log.e("错误码", e.toString());
                    // handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                if (strMsg == null) {
                    strMsg = "--null";
                }
                Log.e("onFailure", "友邻圈获取小区接口" + strMsg);
                //handler.sendEmptyMessage(201);
            }
        });

    }
}


