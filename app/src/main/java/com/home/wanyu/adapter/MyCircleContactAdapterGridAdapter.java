package com.home.wanyu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.WindowUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wanyu on 2017/5/16.
 */

public class MyCircleContactAdapterGridAdapter extends BaseAdapter{
    private Context context;
    private int cout;//图片个数
    List<String> li;
    public MyCircleContactAdapterGridAdapter(List<String> li,Context context){
       this.li=li;
        this.context=context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.gridite,null);
            hodler=new ViewHodler();
            hodler.grid_image_a= (ImageView) convertView.findViewById(R.id.grid_image_1);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
//        Log.i("图片url==",Ip.imagePath+li.get(position));
        int width= WindowUtils.getWinowWidth(context)/6;
        ViewGroup.LayoutParams params= hodler.grid_image_a.getLayoutParams();
        params.height=width;
        params.width=width;
        hodler.grid_image_a.setLayoutParams(params);
        Picasso.with(context).load(Ip.imagePath+li.get(position)).error(R.mipmap.errorphoto).resize(width,width).placeholder(R.mipmap.errorphoto).into(hodler.grid_image_a);
        return convertView;
    }
    class ViewHodler{
        ImageView grid_image_a;
    }
}
