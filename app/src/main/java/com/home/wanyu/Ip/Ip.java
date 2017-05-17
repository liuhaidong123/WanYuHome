package com.home.wanyu.Ip;

/**
 * Created by wanyu on 2017/5/8.
 */

public class Ip {
//    http://192.168.1.55:8080/smarthome/mobileapi/scene/ findList.do?token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String serverPath="http://192.168.1.55:8080/smarthome/mobileapi/";
    public static final String imagePath="http://192.168.1.55:8080/smarthome/";

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
    //删除家人信息:http://192.168.1.55:8080/smarthome/mobileapi/homeUser/delete.do?homePersonalId=&token=9DB2FD6FDD2F116CD47CE6C48B3047EE
    public static final String interface_deleteFamilyUser="homeUser/delete.do?";
}
