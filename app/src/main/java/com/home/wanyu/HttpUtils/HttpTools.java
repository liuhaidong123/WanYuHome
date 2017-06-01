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
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
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
    public void getRecordMsg(final Handler handler, String token, int state, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.GET_RECORD_MSG + "token=" + token + "&state=" + state + "&start=" + start + "&limit=" + limit;

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
    public void recordCancleFinish(final Handler handler, String token, int state, long id) {
        String url = UrlTools.BASE + UrlTools.RRCORD_CANCLE_FINISH + "token=" + token + "&state=" + state + "&id=" + id;

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


    //友邻圈获取我的家接口
    public void getCircleArea(final Handler handler, String token) {
        String url = UrlTools.BASE + UrlTools.GET_CIRCLE_AREA + "token=" + token;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "友邻圈获取我的家接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "友邻圈获取我的家接口" + s);
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
                Log.e("onFailure", "友邻圈获取我的家接口" + strMsg);
                //handler.sendEmptyMessage(201);
            }
        });

    }


    //获取友邻圈小分类接口列表
    public void getCircleSmallType(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.GET_CIRCLE_SMALL_TYPE;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取友邻圈小分类接口列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取友邻圈小分类接口列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleTitleList.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleTitleList.Root.class);
                    message.obj = root;
                    message.what = 111;
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
                Log.e("onFailure", "获取友邻圈小分类接口列表" + strMsg);
                handler.sendEmptyMessage(202);
            }
        });

    }

    //获取友邻圈帖子接口列表
    public void getCircleCardList(final Handler handler, String token, long RQid, int visibleRange, int start, int limit, long categoryId) {
        String url = UrlTools.BASE + UrlTools.GET_CIRCLE_CARD_LIST + "token=" + token + "&RQid=" + RQid + "&visibleRange=" + visibleRange + "&start=" + start + "&limit=" + limit + "&categoryId=" + categoryId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取友邻圈帖子接口列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取友邻圈帖子接口列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleCardList.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleCardList.Root.class);
                    message.obj = root;
                    message.what = 112;
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
                Log.e("onFailure", "获取友邻圈帖子接口列表" + strMsg);
                handler.sendEmptyMessage(203);
            }
        });

    }


    //获取友邻圈点赞结果
    public void getCIrcleLikeResult(final Handler handler, String token, long stateId) {
        String url = UrlTools.BASE + UrlTools.CIRCLE_LIKE + "token=" + token + "&stateId=" + stateId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取友邻圈点赞结果");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取友邻圈点赞结果" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleLike.Root.class);
                    message.obj = root;
                    message.what = 113;
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
                Log.e("onFailure", "获取友邻圈点赞结果" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //学术圈发帖
    public void circlePostCard(final Handler handler, AjaxParams ajaxParams) {

        String url = UrlTools.BASE + UrlTools.CIRCLE_POST_CARD;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "学术圈发帖");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "学术圈发帖" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCirclePostCardResult.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCirclePostCardResult.Root.class);
                    message.obj = root;
                    message.what = 114;
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
                Log.e("onFailure", "学术圈发帖" + strMsg);
                // handler.sendEmptyMessage(201);
            }
        });

    }


    //获取友邻圈评论列表
    public void getCircleCommentList(final Handler handler, String token, long stateId) {
        String url = UrlTools.BASE + UrlTools.GET_CIRCLE_COMMENT_LIST + "token=" + token + "&stateId=" + stateId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取友邻圈评论列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取友邻圈评论列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleCommentMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleCommentMsg.Root.class);
                    message.obj = root;
                    message.what = 115;
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
                Log.e("onFailure", "获取友邻圈评论列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //友邻圈评论接口
    //public void circleComment(final Handler handler, String token, long stateId, long coverPersonalId, String content) {
    public void circleComment(final Handler handler, AjaxParams ajaxParams) {
        //  String url = UrlTools.BASE + UrlTools.CIRCLE_COMMENT + "token=" + token + "&stateId=" + stateId + "&coverPersonalId=" + coverPersonalId + "&content=" + content;
        String url = UrlTools.BASE + UrlTools.CIRCLE_COMMENT;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {


            //   mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "友邻圈评论接口结果");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "友邻圈评论接口结果" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleCommendResult.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleCommendResult.Root.class);
                    message.obj = root;
                    message.what = 116;
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
                Log.e("onFailure", "友邻圈评论接口结果" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //删除圈子帖子接口
    public void getCircleDeleteResult(final Handler handler, String token, long stateId) {
        String url = UrlTools.BASE + UrlTools.CIRCLE_DELETE_CARD + "token=" + token + "&stateId=" + stateId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "删除圈子帖子接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "删除圈子帖子接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getCircleDeleteResult.Root root = mGson.fromJson(s, com.home.wanyu.bean.getCircleDeleteResult.Root.class);
                    message.obj = root;
                    message.what = 117;
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
                Log.e("onFailure", "删除圈子帖子接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区活动首页列表接口
    public void getAreaActivityList(final Handler handler, String token, int over, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.GET_AREA_ACTIVITY_LIST + "token=" + token + "&over=" + over + "&start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区活动首页列表接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区活动首页列表接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityList.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityList.Root.class);
                    message.obj = root;
                    message.what = 118;
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
                Log.e("onFailure", "获取社区活动首页列表接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区活动详情接口
    public void getAreaActivityMsg(final Handler handler, String token, long ActivityId) {
        String url = UrlTools.BASE + UrlTools.GET_AREA_ACTIVITY_MSG + "token=" + token + "&ActivityId=" + ActivityId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区活动详情接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区活动详情接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityMsg.Root.class);
                    message.obj = root;
                    message.what = 119;
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
                Log.e("onFailure", "获取社区活动详情接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区活动点赞接口
    public void getAreaActivityLike(final Handler handler, String token, long ActivityId) {
        String url = UrlTools.BASE + UrlTools.GET_AREA_ACTIVITY_LIKE + "token=" + token + "&ActivityId=" + ActivityId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区活动点赞接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区活动点赞接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 120;
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
                Log.e("onFailure", "获取社区活动点赞接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    // 社区活动添加图片接口
    public void AreaActivityPosImg(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.AREA_ACTIVITY_POST_IMG;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区活动添加图片接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区活动添加图片接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaPostImg.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaPostImg.Root.class);
                    message.obj = root;
                    message.what = 121;
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
                Log.e("onFailure", "社区活动添加图片接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //社区活动评论
    public void AreaActivityComment(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.AREA_ACTIVITY_COMMENT;
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区活动评论");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区活动评论" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityComment.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityComment.Root.class);
                    message.obj = root;
                    message.what = 122;
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
                Log.e("onFailure", "社区活动评论" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区活动加入接口
    public void areaActivityJoin(final Handler handler, String token, long ActivityId) {
        String url = UrlTools.BASE + UrlTools.AREA_ACTIVITY_JOIN + "token=" + token + "&ActivityId=" + ActivityId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区活动加入接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区活动加入接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 123;
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
                Log.e("onFailure", "获取社区活动加入接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    // 社区活动发布接口a
    public void areaActivityPost(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.AREA_ACTIVITY_POST;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区活动发布接口a");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区活动发布接口a" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 124;
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
                Log.e("onFailure", "社区活动发布接口a" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //获取社区活动加入接口
    public void areaActivityDelete(final Handler handler, String token, long ActivityId, long PublisherPersonalId) {
        String url = UrlTools.BASE + UrlTools.AREA_ACTIVITY_DELETE + "token=" + token + "&ActivityId=" + ActivityId + "&PublisherPersonalId=" + PublisherPersonalId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区活动加入接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区活动加入接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 125;
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
                Log.e("onFailure", "获取社区活动加入接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区平车首页列表
    public void getCarPoolingList(final Handler handler, String token, int over, int start, int limit) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_LIST + "token=" + token + "&over=" + over + "&start=" + start + "&limit=" + limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区平车首页列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区平车首页列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.carPoolingList.Root root = mGson.fromJson(s, com.home.wanyu.bean.carPoolingList.Root.class);
                    message.obj = root;
                    message.what = 126;
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
                Log.e("onFailure", "获取社区平车首页列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    // 社区拼车发布接口
    public void carPooloingPost(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_POST;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区拼车发布接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区拼车发布接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 127;
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
                Log.e("onFailure", "社区拼车发布接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //获取社区平车详情
    public void carPoolingMsg(final Handler handler, String token, long carpoolingId) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_MSG + "token=" + token + "&carpoolingId=" + carpoolingId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "获取社区平车详情");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "获取社区平车详情" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.carPoolingMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.carPoolingMsg.Root.class);
                    message.obj = root;
                    message.what = 128;
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
                Log.e("onFailure", "获取社区平车详情" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //社区拼车评论
    public void carPoolingComment(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_COMMENT;
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区拼车评论");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区拼车评论" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityComment.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityComment.Root.class);
                    message.obj = root;
                    message.what = 129;
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
                Log.e("onFailure", "社区拼车评论" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //社区拼车加入接口
    public void carPoolingJoin(final Handler handler, String token, long carpoolingId) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_JOIN + "token=" + token + "&carpoolingId=" + carpoolingId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区拼车加入接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区拼车加入接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityComment.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityComment.Root.class);
                    message.obj = root;
                    message.what = 130;
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
                Log.e("onFailure", "社区拼车加入接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //社区拼车接单接口
    public void carPoolingOrder(final Handler handler, String token, long carpoolingId) {
        String url = UrlTools.BASE + UrlTools.CAR_POOLING_ORDER + "token=" + token + "&carpoolingId=" + carpoolingId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "社区拼车接单接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "社区拼车接单接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityComment.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityComment.Root.class);
                    message.obj = root;
                    message.what = 131;
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
                Log.e("onFailure", "社区拼车接单接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //小区商户接口
    public void shoppingList(final Handler handler, String token, int start, int limit, String rname, double lat2, double lng2) {
        String url = UrlTools.BASE + UrlTools.SOPPING_LIST + "token=" + token + "&start=" + start + "&limit=" + limit + "&rname=" + rname + "&lat2=" + lat2 + "&lng2=" + lng2;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "小区商户接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "小区商户接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.shoppingList.Root root = mGson.fromJson(s, com.home.wanyu.bean.shoppingList.Root.class);
                    message.obj = root;
                    message.what = 132;
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
                Log.e("onFailure", "小区商户接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //家政服务id=11，装修服务id=12接口
    public void homeService(final Handler handler, String token, long businessId) {
        String url = UrlTools.BASE + UrlTools.HOME_SERVICE + "token=" + token + "&businessId=" + businessId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "家政服务接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "家政服务接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.homeService.Root root = mGson.fromJson(s, com.home.wanyu.bean.homeService.Root.class);
                    message.obj = root;
                    message.what = 133;
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
                Log.e("onFailure", "家政服务接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //搜索小区地址
    public void shoppingSearchAddress(final Handler handler, String rname) {
        String url = UrlTools.BASE + UrlTools.SHOPPING_SEARCH_ADDRESS + "rname=" + rname;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "搜索小区地址");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "搜索小区地址" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.shoppingSearchAddress.Root root = mGson.fromJson(s, com.home.wanyu.bean.shoppingSearchAddress.Root.class);
                    message.obj = root;
                    message.what = 135;
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
                Log.e("onFailure", "搜索小区地址" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //未领取的快递
    public void expressNOGet(final Handler handler, String token) {
        String url = UrlTools.BASE + UrlTools.EXPRESS_NO_GET + "token=" + UserInfo.userToken;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "未领取的快递");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "未领取的快递" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.Express.Root root = mGson.fromJson(s, com.home.wanyu.bean.Express.Root.class);
                    message.obj = root;
                    message.what = 136;
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
                Log.e("onFailure", "未领取的快递" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //全部快递
    public void expressAllList(final Handler handler, String token,int start,int limit) {
        String url = UrlTools.BASE + UrlTools.EXPRESS_ALL_LIST + "token=" + UserInfo.userToken+"&start="+start+"&limit="+limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "全部快递");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "全部快递" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.expressList.Root root = mGson.fromJson(s, com.home.wanyu.bean.expressList.Root.class);
                    message.obj = root;
                    message.what = 137;
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
                Log.e("onFailure", "全部快递" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //快递公司信息
    public void expressCompanyMsg(final Handler handler, String token) {
        String url = UrlTools.BASE + UrlTools.EXPRESS_COMPANY_MSG + "token=" + UserInfo.userToken;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "快递公司信息");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "快递公司信息" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.expressCompany.Root root = mGson.fromJson(s, com.home.wanyu.bean.expressCompany.Root.class);
                    message.obj = root;
                    message.what = 138;
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
                Log.e("onFailure", "快递公司信息" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //修改物业地址信息
    public void addressUpdate(final Handler handler, String token,long AddressId) {
        String url = UrlTools.BASE + UrlTools.ADDRESS_UPDATE + "token=" + UserInfo.userToken+"&AddressId="+AddressId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "修改物业地址信息");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "修改物业地址信息" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.addressUpdate.Root root = mGson.fromJson(s, com.home.wanyu.bean.addressUpdate.Root.class);
                    message.obj = root;
                    message.what = 139;
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
                Log.e("onFailure", "修改物业地址信息" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //提交修改物业地址信息
    public void addressUpdateSubmit(final Handler handler, Map<String,String> map) {
        String url = UrlTools.BASE + UrlTools.ADDRESS_UPDATE_SUBMIT;

        mFinalHttp.post(url, new AjaxParams(map), new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "提交修改物业地址信息");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "提交修改物业地址信息" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 140;
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
                Log.e("onFailure", "提交修改物业地址信息" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }
    //删除物业地址信息
    public void addressDelete(final Handler handler, String token,long AddressId) {
        String url = UrlTools.BASE + UrlTools.ADDRESS_DELETE + "token=" + UserInfo.userToken+"&AddressId="+AddressId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "删除物业地址信息");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "删除物业地址信息" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 141;
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
                Log.e("onFailure", "删除物业地址信息" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //友邻圈，社区活动，社区拼车小红点
    public void getRedCircleMsg(final Handler handler, String token,int  msgTypeBegin,int msgTypeEnd ) {
        String url = UrlTools.BASE + UrlTools.GET_RED_CIRCLE_MSG + "token=" + UserInfo.userToken+"&msgTypeBegin="+msgTypeBegin+"&msgTypeEnd="+msgTypeEnd;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "友邻圈，社区活动，社区拼车小红点");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "友邻圈，社区活动，社区拼车小红点" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.redCircleMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.redCircleMsg.Root.class);
                    message.obj = root;
                    message.what = 142;
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
                Log.e("onFailure", "友邻圈，社区活动，社区拼车小红点" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }
    //租房首页列表
    public void getHouseFirstList(final Handler handler, String token, int start,int limit,String cyty) {
        String url = UrlTools.BASE + UrlTools.GET_HOUSE_LIST + "token=" + token+"&start="+start+"&limit="+limit+"&cyty="+cyty;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房首页列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房首页列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HouseFirstList.Root root = mGson.fromJson(s, com.home.wanyu.bean.HouseFirstList.Root.class);
                    message.obj = root;
                    message.what = 143;
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
                Log.e("onFailure", "租房首页列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //租房搜索城市列表
    public void getHouseCityList(final Handler handler, String areaName) {
        String url = UrlTools.BASE + UrlTools.GET_HOUSE_CITY_LIST + "areaName=" + areaName;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房搜索城市列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房搜索城市列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HouseSearchCity.Root root = mGson.fromJson(s, com.home.wanyu.bean.HouseSearchCity.Root.class);
                    message.obj = root;
                    message.what = 144;
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
                Log.e("onFailure", "租房搜索城市列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //租房根据城市搜索小区列表
    public void getHouseAreaByCity(final Handler handler, String rname,String city) {
        String url = UrlTools.BASE + UrlTools.GET_HOUSE_AREA_BY_CITY + "rname=" + rname+"&city="+city;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房根据城市搜索小区列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房根据城市搜索小区列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HouseSearchArea.Root root = mGson.fromJson(s, com.home.wanyu.bean.HouseSearchArea.Root.class);
                    message.obj = root;
                    message.what = 145;
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
                Log.e("onFailure", "租房根据城市搜索小区列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //根据城市和小区搜索所有户型接口列表
    public void getHouseByAreaAndCity(final Handler handler,String token,String cyty,int start,int limit,String residentialQuarters) {
        String url = UrlTools.BASE + UrlTools.GET_HOUSE_BY_AREA_AND_CITY + "token=" + token+"&cyty="+cyty+"&residentialQuarters="+residentialQuarters+"&start="+start+"&limit="+limit;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "根据城市和小区搜索所有户型接口列表");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "根据城市和小区搜索所有户型接口列表" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HouseFirstList.Root root = mGson.fromJson(s, com.home.wanyu.bean.HouseFirstList.Root.class);
                    message.obj = root;
                    message.what = 146;
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
                Log.e("onFailure", "根据城市和小区搜索所有户型接口列表" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //租房信息详情
    public void getHouseMsg(final Handler handler,String token,long rentalID) {
        String url = UrlTools.BASE + UrlTools.GET_HOUSE_MSG + "token=" + token+"&rentalID="+rentalID;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房信息详情");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房信息详情" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HouseMsg.Root root = mGson.fromJson(s, com.home.wanyu.bean.HouseMsg.Root.class);
                    message.obj = root;
                    message.what = 147;
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
                Log.e("onFailure", "租房信息详情" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }


    //租房打电话
    public void houseCallPhone(final Handler handler,String token,long RentalId) {
        String url = UrlTools.BASE + UrlTools.HOUSE_CALL_PHONE + "token=" + token+"&RentalId="+RentalId;

        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房打电话");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房打电话" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 148;
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
                Log.e("onFailure", "租房打电话" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    // 租房发布接口
    public void housePostCard(final Handler handler, AjaxParams ajaxParams) {
        String url = UrlTools.BASE + UrlTools.HOUSE_POST_CARD;
        mFinalHttp.addHeader("Cookie", "token=" + UserInfo.userToken);
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "租房发布接口");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "租房发布接口" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.getAreaActivityLike.Root root = mGson.fromJson(s, com.home.wanyu.bean.getAreaActivityLike.Root.class);
                    message.obj = root;
                    message.what = 149;
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
                Log.e("onFailure", "租房发布接口" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }

    //一个月中发布房源的总数
    public void housePostNumber(final Handler handler, String token){
        String url=UrlTools.BASE+UrlTools.HOUSE_POST_NUMBER+"token="+token;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart", "一个月中发布房源的总数" );
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess", "一个月中发布房源的总数" + s);
                try {
                    Message message = new Message();
                    com.home.wanyu.bean.HousePostNumber.PostNum root = mGson.fromJson(s, com.home.wanyu.bean.HousePostNumber.PostNum.class);
                    message.obj = root;
                    message.what = 150;
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
                Log.e("onFailure", "一个月中发布房源的总数" + strMsg);
                // handler.sendEmptyMessage(203);
            }
        });
    }
}



