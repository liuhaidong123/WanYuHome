package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.home.wanyu.R;

import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeDeviceManagerAdapter extends BaseAdapter{
    Context context;
    public C_MyHomeDeviceManagerAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.c_home_devicemanager_listitem,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
