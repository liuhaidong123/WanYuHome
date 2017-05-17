package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

/**
 * Created by wanyu on 2017/5/16.
 */

public class PopFamilyListAdapter extends BaseAdapter{
    private Context context;
    private String[]Str;
    public PopFamilyListAdapter(Context context,String[]str){
        this.context=context;
        this.Str=str;
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
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.pop_familylistitem,null);
            hodler=new ViewHodler();
            hodler.pop_text_item= (TextView) convertView.findViewById(R.id.pop_text_item);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.pop_text_item.setText(Str[position]);
        return convertView;
    }
    class ViewHodler{
        TextView pop_text_item;
    }
}
