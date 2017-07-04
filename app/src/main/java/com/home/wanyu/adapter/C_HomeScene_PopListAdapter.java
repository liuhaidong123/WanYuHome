package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/7/3.
 */
//情景编辑弹窗中的listivew适配器
public class C_HomeScene_PopListAdapter extends BaseAdapter{
    Context context;
    String[]sceneName={"起床","休息","离家","回家","打游戏","起床","休息","离家","回家","打游戏"};
    public C_HomeScene_PopListAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return sceneName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater. from(context).inflate(R.layout.c_homescene_pop_listitem,null);
            hodler=new ViewHodler();
            hodler.c_homescene_pop_listitem_text= (TextView) convertView.findViewById(R.id.c_homescene_pop_listitem_text);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.c_homescene_pop_listitem_text.setText(sceneName[position]);
        return convertView;
    }
    class ViewHodler{
       TextView c_homescene_pop_listitem_text;
    }
}
