package com.home.wanyu.HttpUtils;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {
    public static final String BASE = "http://192.168.1.33:8080/smarthome";
    //判断有没有用户地址
    //http://192.168.1.55:8080/smarthome/mobileapi/family/findFamilyAddress.do?token=EC9CDB5177C01F016403DFAAEE3C1182
    public static final String HAVE_USER_ADDRESS = "/mobileapi/family/findFamilyAddress.do?";
    //添加用户地址
    public static final String ADD_USER_ADDRESS = "/mobileapi/family/addAddress.do?";

    //获取城市列表
    //http://192.168.1.55:8080/smarthome/mobilepub/baseArea/findList.do
    public static final String GET_CITY_LIST = "/mobilepub/baseArea/findList.do";

    //获取小区列表
    public static final String GET_AREA_LIST = "/mobilepub/residentialQuarters/findAll.do?";//AreaCode=130681

    //根据年份，月份，地址查询业主交的费用
    //http://192.168.1.47:8080/smarthome/mobileapi/detail/findBill.do?token=EC9CDB5177C01F016403DFAAEE3C1182&address=涿州市名流一品小区2号楼2单元5层502号房&Yeartime=2017&monthtime=5
    //token=ACDCE729BCE6FABC50881A867CAFC1BC &address=&Yeartime=&monthtime=13
    public static final String GET_USER_MONEY = "/mobileapi/detail/findBill.do?";

    //返回报修类型
    public static final String GET_REPAIR_TYPE="/mobilepub/repairType/findtypeAll.do";

    //提交报修类型
    //token=ACDCE729BCE6FABC50881A867CAFC1BC&cname=风雪&telephone=18782931356&address=断天涯一单元一号楼&details=屋顶漏水&type=1&processingTime=2017-05-08%2020:09:50
    public static final String SUBMIT_REPAIR_TYPE="/mobileapi/repair/addRecord.do?";
    //获取报修记录详情列表
    public static final String GET_RECORD_MSG="/mobileapi/repair/findRecord.do?";

    //取消维修，完成维修接口
    //token=ACDCE729BCE6FABC50881A867CAFC1BC&id=1&state=2
    public static final String RRCORD_CANCLE_FINISH="/mobileapi/repair/updateState.do?";


    //友邻圈获取小区接口 token=
    public static final String GET_CIRCLE_AREA="/mobileapi/residentialQuarters/findRQ.do?";


    //获取友邻圈小分类接口列表
    public static final String GET_CIRCLE_SMALL_TYPE="/mobilepub/category/findAll.do";

    //获取友邻圈帖子列表
    public static final String GET_CIRCLE_CARD_LIST="/mobileapi/state/findstate.do?";

    //友邻圈点赞接口
    public static final String CIRCLE_LIKE="/mobileapi/state/LikeStat.do?";

    //友邻圈发布状态
    public static final String CIRCLE_POST_CARD="/mobileapi/state/PublishState.do?";

    //友邻圈获取评论列表，点赞头像接口token=EC9CDB5177C01F016403DFAAEE3C1182&stateId=1&coverPersonalId=1
    public static final String GET_CIRCLE_COMMENT_LIST="/mobileapi/state/findStateOne.do?";

    //学术圈评论接口
    public static final String CIRCLE_COMMENT="/mobileapi/state/commentStat.do?";

    //学术圈删除帖子接口
    public static final String CIRCLE_DELETE_CARD="/mobileapi/state/delteState.do?";


    //社区活动首页列表接口
    public static final String GET_AREA_ACTIVITY_LIST="/mobileapi/activity/findActivity.do?";

    //社区活动详情接口
    public static final String GET_AREA_ACTIVITY_MSG="/mobileapi/activity/findActivityOne.do?";

    //社区活动点赞接口
    public static final String GET_AREA_ACTIVITY_LIKE="/mobileapi/upVote/updateActivityLikeNum.do?";

    //社区活动添加图片接口
    public static final String AREA_ACTIVITY_POST_IMG="/mobileapi/activity/AddactivityPictrue.do";

    //社区活动评论接口
    public static final String AREA_ACTIVITY_COMMENT="/mobileapi/activity/addcomment.do?";

    //社区活动加入
    public static final String AREA_ACTIVITY_JOIN="/mobileapi/activityLog/updateActivityparticipateNumber.do?";

    //发布社区活动
    public static final String AREA_ACTIVITY_POST="/mobileapi/activity/PublishActivity.do?";

    //删除活动
    public static final String AREA_ACTIVITY_DELETE="/mobileapi/activity/delete.do?";

    //社区平车首页接口
    public static final String CAR_POOLING_LIST="/mobileapi/carpooling/findcarpoolingAll.do?";

    //社区发布活动
    public static final String CAR_POOLING_POST="/mobileapi/carpooling/PublishCarpooling.do?";

    //社区拼车详情
    public static final String CAR_POOLING_MSG="/mobileapi/carpooling/findCarpoolingOne.do?" ;


    //社区拼车评论
    public static final String CAR_POOLING_COMMENT="/mobileapi/carpooling/addcommentCarpooling.do?";

    //社区加入接口
    public static final String CAR_POOLING_JOIN="/mobileapi/carpoolingLog/addCarpoolingLog.do?" ;
    //接单
    public static final String CAR_POOLING_ORDER="/mobileapi/carpoolingLog/addCarpoolingLog.do?" ;

//小区商户列表
    public static final String SOPPING_LIST="/mobileapi/business/findBusinessList.do?";


    //搜索小区地址
    public static final String SHOPPING_SEARCH_ADDRESS="/mobilepub/residentialQuarters/FuzzyQuery.do?" ;

    //家政服务接口
    public static final String HOME_SERVICE="/mobileapi/business/findbusinessOne.do?" ;


    //获取物业管家首页未收取的快递
    public static final String EXPRESS_NO_GET="/mobileapi/takeExpress/findListNot.do?";

    //获取所有快递
    public static final String EXPRESS_ALL_LIST="/mobileapi/takeExpress/findList.do?";

    //获取快递公司信息
    public static final String EXPRESS_COMPANY_MSG="/mobileapi/express/findList.do?";

    //修改物业账单地址
    public static final String ADDRESS_UPDATE="/mobileapi/detailHome/get.do?";

    //提交修改地址
    public static final String ADDRESS_UPDATE_SUBMIT="/mobileapi/detailHome/UpdateDetailHomeAddress.do?";

    //删除物业地址
    public static final String ADDRESS_DELETE="/mobileapi/family/deleteDetailHomeAddress.do?";
}

