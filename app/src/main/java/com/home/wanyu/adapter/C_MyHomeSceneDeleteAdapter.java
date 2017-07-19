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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/17.
 */

public class C_MyHomeSceneDeleteAdapter extends BaseAdapter implements View.OnClickListener{
    Select isAllSelect;
    ArrayList<Integer> list;
    Context context;
    public C_MyHomeSceneDeleteAdapter(Context context,ArrayList<Integer>li,Select isAllSelect){
        this.context=context;
        this.list=li;
        this.isAllSelect=isAllSelect;
    }
    @Override
    public int getCount() {
        return list.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.c_home_scenemanager_delete_listitem,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        if (list.get(position)==0){
            viewHodler.c_home_scene_image_select.setSelected(false);
        }
        else {
            viewHodler.c_home_scene_image_select.setSelected(true);
        }

        viewHodler.c_home_scene_image_select.setTag(position);
        viewHodler.c_home_scene_image_select.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int pos= (int) v.getTag();
        if (v.isSelected()){
            list.set(pos,0);
            v.setSelected(false);
        }
        else {
            list.set(pos,1);
            v.setSelected(true);
        }
        isAllSelect.isAllSelect(isAllSelect());
    }

    class ViewHodler{
        @BindView(R.id.c_home_scene_image_select)ImageView c_home_scene_image_select;//选择按钮
        @BindView(R.id.c_home_scene_delete_devicename)TextView c_home_scene_delete_devicename;
        @BindView(R.id.c_home_scene_image_delete)RoundImageView c_home_scene_image_delete;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }

    public interface Select{
        void isAllSelect(Boolean select);
    }
    public boolean isAllSelect(){
        boolean flag=true;
        for (int i=0;i<list.size();i++){
            if (list.get(i)==0){
                flag=false;
            }
        }
        return flag;
    }
}
