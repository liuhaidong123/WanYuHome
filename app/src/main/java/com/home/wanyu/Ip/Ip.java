package com.home.wanyu.Ip;

/**
 * Created by wanyu on 2017/5/8.
 */

public class Ip {
//    http://192.168.1.55:8080/smarthome/mobileapi/scene/ findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String serverPath="http://192.168.1.55:8080/smarthome/mobileapi/";
    public static final String imagePath="http://192.168.1.55:8080/smarthome";
    public static final String pths="http://192.168.1.55:8080/smarthome/mobilepub";
//    家》设备页面获取房间列表接口, 家》情景页面获取情景列表接口
    public static final String interface_Home_getSceneAndRoom="scene/findList.do?";

//    家》设备页面当前选中的房间一键开启/关闭功能接口（ |state       |Integer  |状态0=开1=关）
// http://192.168.1.55:8080/smarthome/mobileapi/room/roomctrl.do?id=1&state=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeRoom_OpenAndOn="room/roomctrl.do?";

//    家》情景页面当前选中的情景一键开启功能接口
//    家》情景页面当前选中的情景一键关闭功能接口
//    http://192.168.1.55:8080/smarthome/mobileapi/scene/scenectrl.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_OpenAndOn="scene/scenectrl.do?";

    //获取某个情景信息http://192.168.1.55:8080/smarthome/mobileapi/scene/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_getScene="scene/get.do?";

    //获取某个房间信息http://192.168.1.55:8080/smarthome/mobileapi/room/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_getRoom="room/get.do?";

    //情景修改http://192.168.1.55:8080/smarthome/mobileapi/scene/save.do?id=123&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_setScene="scene/save.do?";

    //房间修改http://192.168.1.55:8080/smarthome/mobileapi/room/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_setRoom="room/save.do?";

    //设备当前状态信息修改:http://192.168.1.55:8080/smarthome/mobileapi/equipment/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_HomeScene_setDevice="equipment/save.do?";

    //获取所有设备
    //http://192.168.1.55:8080/smarthome/mobileapi/equipment/findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取所有设备列表
    public static final String interface_HomeScene_getAllDevice="equipment/findList.do?";

    //添加／修改设备http://192.168.1.55:8080/smarthome/mobileapi/equipment/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_DeviceAdd_Change="equipment/save.do?";

    //获取家人列表http://192.168.1.55:8080/smarthome/mobileapi/homeUser/findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getFamilyUsers="homeUser/findList.do?";
    //删除家人信息:http://192.168.1.55:8080/smarthome/mobileapi/myFamily/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_deleteFamilyUser="myFamily/delete.do?";

    //获取个人信息接口http://192.168.1.55:8080/smarthome/mobileapi/personal/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取用户个人信息
    public static final String interface_getUserInfo="personal/get.do?";
    //个人信息修改（添加不使用此接口）:http://192.168.1.55:8080/smarthome/mobileapi/personal/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_changeUserInfo="personal/save.do?";

    //获取我的房屋信息:http://192.168.1.55:8080/smarthome/mobileapi/family/get.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getHouseInfo="family/get.do?";

    //修改我的房屋 http://192.168.1.55:8080/smarthome/mobileapi/family/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_setHouseInfo="family/save.do?";

    //删除情景http://192.168.1.55:8080/smarthome/mobileapi/scene/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_deleteScene="scene/delete.do?";

    //删除房间: http://192.168.1.55:8080/smarthome/mobileapi/room/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_deleteRoom="/room/delete.do?";

    //获取城市:http://192.168.1.55:8080/smarthome/mobilepub/baseArea/findList.do
    public static final String interface_getCity="/baseArea/findList.do";
    //获取小区http://192.168.1.55:8080/smarthome/mobilepub/residentialQuarters/findAll.do?AreaCode=130681
    public static final String interface_getArea="/residentialQuarters/findAll.do?";

    //获取家人权限接口:http://192.168.1.55:8080/smarthome/mobileapi/myFamily/get.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
    public static final String interface_getFamilyUserPri="myFamily/get.do?";

    //修改家人权限接口:http://192.168.1.55:8080/smarthome/mobileapi/myFamily/setPermission.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:GET
    public static final String interface_changeFamilyUserPri="myFamily/setPermission.do?";

    //    添加家人的获取验证码接口http://192.168.1.55:8080/smarthome/mobileapi/personal/addvcode.do?
    public static final String interface_addFamilyUserSms="personal/addvcode.do?";
    //    添加家人的提交验证码与电话号http://192.168.1.55:8080/smarthome/mobileapi/myFamily/savetest.do?
    public static final String interface_sendPhoneSmsCode="myFamily/savetest.do?";
    //    添加家人http://192.168.1.55:8080/smarthome/mobileapi/myFamily/save.do?
    public static final String interface_saveFamilyUser="myFamily/save.do?";


    //   意见反馈http://192.168.1.55:8080/smarthome/mobileapi/feedback/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_feadUs="feedback/save.do?";


    //   获取我的所有家:http://192.168.1.55:8080/smarthome/mobileapi/family/findMyRelativeHome.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getAllFamily="family/findMyRelativeHome.do?";


    //   意见反馈http://192.168.1.55:8080/smarthome/mobileapi/personal/setFamily.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&familyId=123&personalId=123234
    public static final String interface_saveMyFamily="personal/setFamily.do?";

    //   修改密码://192.168.1.55:8080/smarthome/mobileapi/personal/mdfPasswd.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&oldPwd=123&newPwd=123234
    public static final String interface_changePsd="personal/mdfPasswd.do?";

    //   关于我们:http://192.168.1.55:8080/smarthome/mobilepub/aboutUs/get.do
    public static final String interface_aboutUs="aboutUs/get.do";

//    http://192.168.1.55:8080/smarthome/mobileapi/state/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取我的圈子
    public static final String interface_getMyCircle="state/findPage.do?";
    //http://192.168.1.55:8080/smarthome/mobileapi/state/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
    public static final String interface_deleteMyCircle="state/delete.do?";



    //   http://192.168.1.55:8080/smarthome/mobileapi/activity/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE获取我的huodong
    public static final String interface_getMyActivity="activity/findPage.do?";
    //http://192.168.1.55:8080/smarthome/mobileapi/activity/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
//    Method:POST
    public static final String interface_deleteMyActivity="activity/delete.do?";

    //开锁记录http://192.168.1.55:8080/smarthome/mobileapi/unlockRecords/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&equipmentId=6
    public static final String interface_getLockRecord="unlockRecords/findPage.do?";
    //分享钥匙:http://192.168.1.55:8080/smarthome/mobileapi/equipment/doorShareKey.do?id=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_shareLock="equipment/doorShareKey.do?";

    //修改手机号时获取验证码修改绑定手机号发送验证码接口
//    http://192.168.1.55:8080/smarthome/mobileapi/personal/bindvcode.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&telephone=13717883005
    public static final String interface_changePhoneSmsCode="personal/bindvcode.do?";
    //    修改绑定手机号http://192.168.1.55:8080/smarthome/mobileapi/personal/mdfTelephone.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&telephone=13717883005&pwd=123456
    public static final String interface_changePhone="personal/mdfTelephone.do?";

    //获取他人的个人信息http://192.168.1.55:8080/smarthome/mobileapi/personal/gettata.do?tataId=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getOtherPersonMsg="personal/gettata.do?";
    //他人圈子http://192.168.1.55:8080/smarthome/mobileapi/state/findPageTata.do?tataId=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getOtherPersonCircle="state/findPageTata.do?";
    //获取他人圈子 http://192.168.1.55:8080/smarthome/mobileapi/activity/findPageTata.do?tataId=1&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_getOtherPersonActivity="activity/findPageTata.do?";

    //密码解锁http://192.168.1.55:8080/smarthome/mobileapi/equipment/doorOpenPasswd.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&equipmentId=6&pwd=123
    public static final String interface_openDoorPsd="equipment/doorOpenPasswd.do?";

    //修改门锁密码:http://192.168.1.55:8080/smarthome/mobileapi/equipment/doorPasswd.do?
    public static final String interface_changeDoorPsd="equipment/doorPasswd.do?";


    //注册获取验证码:http://192.168.1.55:8080/smarthome/mobilepub/personal/vcode.do?telephone=18782931356
    public static final String interface_getSMSCODE_register="/personal/vcode.do?";
    //注册http://192.168.1.55:8080/smarthome/mobilepub/personal/register.do?telephone=18782931356&password=123&vcode=000886
    public static final String interface_get_register="/personal/register.do?";

    //获取消息接口http://192.168.1.55:8080/smarthome/mobileapi/message/findPage.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE&msgType=&msgTypeBegin=2&msgTypeEnd=3
    public static final String interface_get_Message="message/findPage.do?";
    //消息已读设置:http://192.168.1.55:8080/smarthome/mobileapi/messageLog/save.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_MessageRead="messageLog/save.do?";
    //删除消息http://192.168.1.55:8080/smarthome/mobileapi/message/delete.do?ids=1234,12345&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_MessageDelete="message/delete.do?";

    //获取有无未读消息http://192.168.1.55:8080/smarthome/mobileapi/message/hasmessage.do?msgType=&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_MessageisRead="message/hasmessage.do?";


    //登录:http://localhost:8080/smarthome/mobilepub/personal/login.do?telephone=18782931356&password=123
    public static final String interface_Login="/personal/login.do?";
    //登录验证码:http://localhost:8080/smarthome/mobilepub/personal/loginvcode.do?telephone=18782931356
    public static final String interface_LoginSMSCODE="/personal/loginvcode.do?";

    //退出登录:http://localhost:8080/smarthome/mobilepub/personal/Exitlogin.do?token=ACDCE729BCE6FABC50881A867CAFC1BC
    public static final String interface_ClearLogin="/personal/Exitlogin.do?";



}
