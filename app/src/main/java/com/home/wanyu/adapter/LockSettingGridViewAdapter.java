package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/11.
 */

public class LockSettingGridViewAdapter extends BaseAdapter {

    private String[]Str;
    private Context context;
    public LockSettingGridViewAdapter(String[]Str,Context context){
        this.Str=Str;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Str==null?0:Str.length;
    }

    @Override
    public Object getItem(int position) {
        return Str[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.locksetting_griditem,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.lockSetting_gridItem_text.setText(Str[position]);
        return convertView;
    }

    class   ViewHodler {
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.lockSetting_gridItem_text)TextView lockSetting_gridItem_text;
    }
}
