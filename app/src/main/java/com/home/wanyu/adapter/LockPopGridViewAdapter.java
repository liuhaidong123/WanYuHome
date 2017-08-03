package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.wanyu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/21.
 */

public class LockPopGridViewAdapter extends BaseAdapter{
    String[] str={"1","2","3","4","5","6","7","8","9","x","0","确认开锁"};
    Context context;
    public LockPopGridViewAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return str.length;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.pop_lock_gridview_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.pop_lock_text.setText(str[position]);
        if (position==9){
            hodler.pop_lock_text.setVisibility(View.GONE);
            hodler.pop_lock_image.setVisibility(View.VISIBLE);
        }
        if (position==11){
            hodler.pop_lock_text.setTextColor(context.getResources().getColor(R.color.themColor));
        }
        return convertView;
    }
    class ViewHodler{
        @BindView(R.id.pop_lock_text)TextView pop_lock_text;
        @BindView(R.id.pop_lock_image)ImageView pop_lock_image;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
