package com.home.wanyu.C_View;

import android.app.Activity;

import com.home.wanyu.C_Model.C_MyHomeDeviceDeleteModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.bean.Bean_AllDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/7/28.
 */

public class C_MyHomeDeviceDeletePresenter {
    Activity activity;
    C_MyHomeDeviceDeleteModel model;
    public C_MyHomeDeviceDeletePresenter(Activity activity, ILoading iLoading, C_MyHomeDeviceDeleteModel.IDeviceDelete iDeviceDelete)
    {
        this.activity=activity;
        model=new C_MyHomeDeviceDeleteModel(iLoading,iDeviceDelete);
    }
    public void deleteDevice(List<Bean_AllDevice.EquipmentListBean> lt){
        if (lt==null|lt.size()==0){
            return;
        }
        List<Bean_AllDevice.EquipmentListBean>lm=new ArrayList<>();
        for (int i=0;i<lt.size();i++){
            if (lt.get(i).isFlag()){
                lm.add(lt.get(i));
            }
        }
        if (lm!=null&&lm.size()>0){
            model.delete(lm);
        }
        else {
            mToast.Toast(activity,"请先选择要删除的设备");
            }
    }
}
