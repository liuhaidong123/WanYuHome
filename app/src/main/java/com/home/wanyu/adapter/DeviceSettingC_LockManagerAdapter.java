package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/24.
 */

public class DeviceSettingC_LockManagerAdapter extends BaseAdapter {
    Context context;
    public DeviceSettingC_LockManagerAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 10;
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
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.lock_manager_listviewitem,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.lockmanager_listitem_image)RoundImageView lockmanager_listitem_image;//touxiang
        @BindView(R.id.lockmanager_listitem_name)TextView lockmanager_listitem_name;//mingzi
        @BindView(R.id.lockmanager_listitem_nick)TextView lockmanager_listitem_nick;//家人／租户／朋友 昵称显示
        @BindView(R.id.lockmanager_imageEdit)ImageView lockmanager_imageEdit;//编辑的image
        @BindView(R.id.lockmanager_lockTime)TextView lockmanager_lockTime;//显示钥匙时限的view
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
