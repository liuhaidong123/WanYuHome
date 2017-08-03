package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_ShareMumber;
import com.home.wanyu.myview.RoundImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wanyu on 2017/7/21.
 */

public class DeviceSettingC_LockShareAdapter extends BaseAdapter{

    Context context;
    ArrayList<Bean_ShareMumber> list;
    public DeviceSettingC_LockShareAdapter(Context context, ArrayList<Bean_ShareMumber> list){
        this.context=context;
        this.list=list;
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
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.lockshare_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        if (list.get(position).isSelect()){
            hodler.lockshare_item_layout.setSelected(true);
        }
        else {
            hodler.lockshare_item_layout.setSelected(false);
        }

        return convertView;
    }

    class ViewHodler{
        @BindView(R.id.lockshare_item_layout)RelativeLayout lockshare_item_layout;
        @BindView(R.id.lockshare_item_image)RoundImageView lockshare_item_image;
        @BindView(R.id.lockshare_item_textv_name)TextView lockshare_item_textv_name;//名字
        @BindView(R.id.lockshare_item_textv_alias)TextView lockshare_item_textv_alias;//用户类型（租户，家人，朋友）
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
