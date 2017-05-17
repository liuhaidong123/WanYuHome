package com.home.wanyu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.WindowUtils;

/**
 * Created by wanyu on 2017/5/16.
 */

public class MyCircleContactAdapterGridAdapter extends BaseAdapter{
    private Context context;
    private int cout;//图片个数
    private int[] resId={R.mipmap.ph1,R.mipmap.ph2,R.mipmap.ph3,R.mipmap.ph4,R.mipmap.ph5};
    public MyCircleContactAdapterGridAdapter(int count,Context context){
        this.cout=count;
        this.context=context;
    }
    @Override
    public int getCount() {
        return cout;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.gridite,null);
            hodler=new ViewHodler();
            hodler.grid_image_a= (ImageView) convertView.findViewById(R.id.grid_image_1);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.grid_image_a.setImageResource(resId[position%resId.length]);
        int width= WindowUtils.getWinowWidth(context)/5;
        ViewGroup.LayoutParams params= hodler.grid_image_a.getLayoutParams();
        params.width=width;
        params.height=width;
        hodler.grid_image_a.setLayoutParams(params);
        return convertView;
    }
    class ViewHodler{
        ImageView grid_image_a;
    }

}
