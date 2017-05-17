package com.home.wanyu.HttpUtils;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {
    public static final String BASE = "http://192.168.1.55:8080/smarthome";
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
}
