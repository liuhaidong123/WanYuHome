package com.home.wanyu.C_View;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.home.wanyu.C_Model.C_MyHomeModel;
import com.home.wanyu.C_Model.ILoading;
import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.adapter.HouseAdapter;
import com.home.wanyu.bean.Bean_getMyFamily;

import java.util.List;

/**
 * Created by wanyu on 2017/7/13.
 */

public class C_MyHomePresenter {
    C_MyHomeModel model;
    IPresneter iPresneter;
    PopupWindow pop;
    Activity activity;
    public C_MyHomePresenter(C_MyHomeModel.IC_MyHome ic_myHome, ILoading iLoading, Activity activity,IPresneter iPresneter){
        model=new C_MyHomeModel(ic_myHome,iLoading);
        this.activity=activity;
        this.iPresneter=iPresneter;
    }
    public void getAllMyFamily(){
        model.getAllMyArea();//获取所有的我的家
    }

    //弹窗切换家
    public void showWindow(final List<Bean_getMyFamily.MapListBean> li) {
            if (li==null|li.size()==0){
                return;
            }
            if(pop==null){
                pop = new PopupWindow();
            }
            View v = LayoutInflater.from(activity).inflate(R.layout.pop_house, null);
            ListView pop_family_list= (ListView) v.findViewById(R.id.pop_family_list_1);
            HouseAdapter adapter=new HouseAdapter(activity,li);
            pop_family_list.setAdapter(adapter);
            pop_family_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    model.saveFamiley(li.get(position));
                    pop.dismiss();
                }
            });
            View parent =activity.findViewById(R.id.c__my_home_LayoutName);

            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setWidth(parent.getMeasuredWidth());

            pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            pop.setContentView(v);
            pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            pop.setTouchable(true);
            pop.setFocusable(true);
            pop.setOutsideTouchable(true);

            pop.setAnimationStyle(R.style.popup4_anim);
            pop.showAsDropDown(parent, 0, 2);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                   iPresneter.onDissmiss();
                }
            });
    }

    public interface IPresneter{
        void onDissmiss();
    }
}
