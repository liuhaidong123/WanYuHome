package com.home.wanyu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.activity.MyHouseFamilyInfoActivity;
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
 * Created by wanyu on 2017/5/15.
 */

public class MyHouseFamilyManagerAdapter extends BaseAdapter{
    private int current;
    private Context context;
//    private List<Map<String,String>> list;
//    private String[]pro={"租户","游客","弟弟","妹妹","哥哥","姐姐"};
//    private String[] name={"LIM","ZY","刘文","刘一"};
//    private int[] ResId={R.mipmap.it1,R.mipmap.it2,R.mipmap.it3};
    private List<Bean_FamilyUserS.PersonalListBean> list;
    public MyHouseFamilyManagerAdapter(Context context,List<Bean_FamilyUserS.PersonalListBean> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.familymanager_list_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.lockshare_list_item_text.setText(list.get(position).getTrueName());
        int type=list.get(position).getUserType();
        String tp;
        switch (type){//userType|成员类型0=家庭成员1=租客2=访客|成员类型0=家庭成员1=租客2=访客
            case 0:
                tp="家庭成员";
                break;
            case 1:
                tp="租客";
                break;
            case 2:
                tp="访客";
                break;
            default:
                tp="自定义类型";
                break;
        }
        hodler.lockshare_list_item_text_pro.setText(tp);
        Picasso.with(context).load(Ip.imagePath+list.get(position).getAuthentication()).error(R.mipmap.errorphoto).into(hodler.lockshare_list_item_image);
//        if ("1".equals(list.get(position).get("select"))){
//            hodler.locklayout.setSelected(true);
//        }
//        else if ("0".equals(list.get(position).get("select"))){
//            hodler.locklayout.setSelected(false);
//        }
//        current=position;
        hodler.locklayout.setTag(position);
        hodler.locklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",list.get(pos));
                intent.putExtra("data",bundle);
                intent.setClass(context,MyHouseFamilyInfoActivity.class);
                context.startActivity(intent);

            }
        });
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.lockshare_list_item_text_2)TextView lockshare_list_item_text;
        @BindView(R.id.lockshare_list_item_text_pro_2)TextView lockshare_list_item_text_pro;
        @BindView(R.id.lockshare_list_item_image_2)RoundImageView lockshare_list_item_image;
        @BindView(R.id.locklayout_2)RelativeLayout locklayout;
    }

//    public void init(){
//        list=new ArrayList<>();
//        for (int i=0;i<7;i++){
//            Map<String,String>mp=new HashMap<>();
//            mp.put("url",ResId[i%ResId.length]+"");
//            mp.put("pro",pro[i%pro.length]);
//            mp.put("name",name[i%name.length]);
//            mp.put("select","0");
//            list.add(mp);
//        }
//    }
}
