package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_getMyFamily;

import java.util.List;

/**
 * Created by wanyu on 2017/5/18.
 */

public class HouseAdapter extends BaseAdapter{
    private Context context;
    List<Bean_getMyFamily.MapListBean> li;
    public HouseAdapter(Context context, List<Bean_getMyFamily.MapListBean> li){
        this.context=context;
        this.li=li;
    }
    @Override
    public int getCount() {
        return li==null?0:li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.houseadapter_item,null);
            hodler=new ViewHodler();
            hodler.pop_text_item= (TextView) convertView.findViewById(R.id.pop_text_item_1);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.pop_text_item.setText(li.get(position).getFamilyName());
        return convertView;
    }
    class ViewHodler{
        TextView pop_text_item;
    }
}
