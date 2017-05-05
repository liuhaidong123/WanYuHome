package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.home.wanyu.R;

import java.util.ArrayList;
import java.util.HashMap;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/5.
 */

public class HomeDeviceAddWifiSettingGridViewAdapter extends BaseAdapter{
    private ArrayList<HashMap<String,String>> list;
    private Context context;
    public HomeDeviceAddWifiSettingGridViewAdapter(ArrayList<HashMap<String,String>> list,Context context){
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
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_homedeviceaddwifisettinggrid_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
            }
            if ("1".equals(list.get(position).get("select"))){
                hodler.addwifisetting_layout.setSelected(true);
            }
        else {
                hodler.addwifisetting_layout.setSelected(false);
            }
        hodler.addwifisetting_layout.setTag(position);
        hodler.addwifisetting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if (pos!=list.size()-1){
                    setSelct(pos);
                }
               else {
                    Toast.makeText(context,"更多图标，尽情期待",Toast.LENGTH_SHORT).show();
                }
            }
        });
        hodler.wifisettingGrid_item_image.setImageResource(Integer.parseInt(list.get(position).get("image")));
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View convertView){
            ButterKnife.bind(this,convertView);
        }
            @BindView(R.id.wifisettingGrid_item_image)ImageView wifisettingGrid_item_image;
        @BindView(R.id.addwifisetting_layout)RelativeLayout addwifisetting_layout;
    }

    public void setSelct(int pos){
        int size=list.size();
        for (int i=0;i<size;i++){
            list.get(i).put("select","0");
        }
        list.get(pos).put("select","1");
            notifyDataSetChanged();
    }
}
