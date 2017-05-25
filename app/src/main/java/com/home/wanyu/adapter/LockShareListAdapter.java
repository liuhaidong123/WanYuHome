package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.bean.Bean_FamilyUserS;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/11.
 */

public class LockShareListAdapter extends BaseAdapter{
    private Context context;
    List<Bean_FamilyUserS.PersonalListBean> list;
    SelectCurrent se;
    public LockShareListAdapter(Context context,List<Bean_FamilyUserS.PersonalListBean> list,SelectCurrent se){
        this.context=context;
        this.list=list;
        this.se=se;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.lockshare_list_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.lockshare_list_item_text.setText(list.get(position).getUserName());
        hodler.lockshare_list_item_text_pro.setText(list.get(position).getComment());
        Picasso.with(context).load(Ip.imagePath+list.get(position).getAvatar()).error(R.mipmap.errorphoto).into(hodler.lockshare_list_item_image);

        if (list.get(position).isSele()){
            hodler.locklayout.setSelected(true);
        }
        else {
            hodler.locklayout.setSelected(false);
        }
//        current=position;
        hodler.locklayout.setTag(position);
        hodler.locklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                se.sele(pos);
                setSelect(pos);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.lockshare_list_item_text)TextView lockshare_list_item_text;
        @BindView(R.id.lockshare_list_item_text_pro)TextView lockshare_list_item_text_pro;
        @BindView(R.id.lockshare_list_item_image)RoundImageView lockshare_list_item_image;
        @BindView(R.id.locklayout)RelativeLayout locklayout;
    }

    public void setSelect(int pos){
        for (int i=0;i<list.size();i++){
            list.get(i).setSele(false);
        }
        list.get(pos).setSele(true);
    }
    public interface SelectCurrent{
        void sele(int pos);
    }
}
