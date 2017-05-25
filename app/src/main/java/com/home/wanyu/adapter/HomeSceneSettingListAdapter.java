package com.home.wanyu.adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.lzhView.MyFloatingView;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/5/4.
 */

public class HomeSceneSettingListAdapter extends BaseAdapter{
    private List<Bean_getSceneData.EquipmentListBean>list;
    private Context context;
    public HomeSceneSettingListAdapter(List<Bean_getSceneData.EquipmentListBean>list,Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.homescenesetting_listview_item,null);
            viewHodler=new ViewHodler();
            viewHodler.activity_homeSceneSetting_listitem_rela_switch= (SwitchCompat) convertView.findViewById(R.id.activity_homeSceneSetting_listitem_rela_switch);
            viewHodler.activity_homeSceneSetting_listitem_rela_image= (ImageView) convertView.findViewById(R.id.activity_homeSceneSetting_listitem_rela_image);
            viewHodler.activity_homeSceneSetting_listitem_rela_textSetting= (TextView) convertView.findViewById(R.id.activity_homeSceneSetting_listitem_rela_textSetting);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        if (list.get(position).getIconId()>=0&&list.get(position).getIconId()<=icon.mIconId[icon.mIconId.length-1]){
            viewHodler.activity_homeSceneSetting_listitem_rela_image.setImageResource(icon.mIconRes[list.get(position).getIconId()]);
        }
       else {
            viewHodler.activity_homeSceneSetting_listitem_rela_image.setImageResource(R.mipmap.error_big);
        }
        viewHodler.activity_homeSceneSetting_listitem_rela_textSetting.setText(list.get(position).getName());
      int state=list.get(position).getToState();
        //0开启状态，1关闭状态
        if (state==1){
            viewHodler.activity_homeSceneSetting_listitem_rela_switch.setChecked(false);
        }
        else if (state==0){
            viewHodler.activity_homeSceneSetting_listitem_rela_switch.setChecked(true);
            }
        viewHodler.activity_homeSceneSetting_listitem_rela_switch.setTag(position);

        viewHodler.activity_homeSceneSetting_listitem_rela_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos= (int) buttonView.getTag();
                if (isChecked){
                    list.get(pos).setToState(0);
                }
                else {
                    list.get(pos).setToState(1);
                }
            }
        });
        return convertView;
    }

    class ViewHodler{
        ImageView activity_homeSceneSetting_listitem_rela_image;
        TextView activity_homeSceneSetting_listitem_rela_textSetting;
        SwitchCompat activity_homeSceneSetting_listitem_rela_switch;
    }

}
