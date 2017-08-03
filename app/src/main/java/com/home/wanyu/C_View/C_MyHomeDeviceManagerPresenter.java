package com.home.wanyu.C_View;

import android.widget.ListView;

import com.home.wanyu.C_Model.C_MyHomeDeviceManagerModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.adapter.C_MyHomeDeviceManagerAdapter;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeDeviceManagerPresenter {
    C_MyHomeDeviceManagerModel model;
    public C_MyHomeDeviceManagerPresenter(ILoading iLoading, C_MyHomeDeviceManagerModel.IAllDeviceModel iAllDeviceModel){
        model=new C_MyHomeDeviceManagerModel(iLoading,iAllDeviceModel);
    }

    public void getAllDevice(){
        model.getMyDevice();
    }
}
