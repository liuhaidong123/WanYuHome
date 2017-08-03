package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.List;

/**
 * Created by wanyu on 2017/7/25.
 */

public class C_HomeRoom_PopListAdapter extends BaseAdapter{
    Context context;

    List<String> list;
    public C_HomeRoom_PopListAdapter(Context context,List<String> list){
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
            convertView= LayoutInflater. from(context).inflate(R.layout.c_homescene_pop_listitem,null);
            hodler=new ViewHodler();
            hodler.c_homescene_pop_listitem_text= (TextView) convertView.findViewById(R.id.c_homescene_pop_listitem_text);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.c_homescene_pop_listitem_text.setText(list.get(position));
        return convertView;
    }
    class ViewHodler{
        TextView c_homescene_pop_listitem_text;
    }
}
