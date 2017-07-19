package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeSceneManagerAdapter extends BaseAdapter{
    Context context;
    public C_MyHomeSceneManagerAdapter(Context context){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.c_home_scenemanager_listitem,null);//c_home_scenemanager_listitem
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.c_home_scene_image)RoundImageView c_home_scene_image;
        @BindView(R.id.c_home_scene_text)TextView c_home_scene_text;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
