package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/5/5.
 */

public class PopDataGridViewAdapter extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;
    public PopDataGridViewAdapter(List<Map<String,String>> list,Context context){
        this.list=list;
        this.context=context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.pop_data_gridview_item,null);
            viewHodler=new ViewHodler();
            viewHodler.pop_data_gridview_item_text= (TextView) convertView.findViewById(R.id.pop_data_gridview_item_text);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }
        if (position!=list.size()-1){
            if ("1".equals(list.get(position).get("select"))){
                viewHodler.pop_data_gridview_item_text.setSelected(true);
                }
            else {
                viewHodler.pop_data_gridview_item_text.setSelected(false);
            }
        }
        else {
                viewHodler.pop_data_gridview_item_text.setSelected(false);
                }

        viewHodler.pop_data_gridview_item_text.setText(list.get(position).get("name"));
        return convertView;
    }
    class ViewHodler{
        TextView pop_data_gridview_item_text;
    }
}
