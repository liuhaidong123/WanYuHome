package com.home.wanyu.apater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Icons.icon;
import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.bean.Bean_getSceneData;
import com.home.wanyu.lzhView.SwitchButton;
import com.home.wanyu.myview.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/7/5.
 */

public class C_HomeRoomGridAdapter extends BaseAdapter{
    private List<Bean_getSceneData.EquipmentListBean> list;
    private Context context;
    public C_HomeRoomGridAdapter(List<Bean_getSceneData.EquipmentListBean>list,Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list==null?1:list.size()+1;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.c_homedevice_grid_item,null);
            viewHodler=new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler= (ViewHodler) convertView.getTag();
        }

        if (position!=getCount()-1){//不是添加选项
            if (list.get(position).getIconId()>=0&&list.get(position).getIconId()<= icon.mIconId[icon.mIconId.length-1]){
                viewHodler.c_homescene_gridViewItem_image.setImageResource(icon.mIconRes[list.get(position).getIconId()]);
            }
            else {
                viewHodler.c_homescene_gridViewItem_image.setImageResource(R.mipmap.error_big);
            }
            viewHodler.c_homescene_gridViewItem_textView.setText(list.get(position).getName());
            int state=list.get(position).getToState();
            //0开启状态，1关闭状态
            if (state==1){
                viewHodler.c_homescene_gridViewItem_switch.setNorState(false);
            }
            else if (state==0){
                viewHodler.c_homescene_gridViewItem_switch.setNorState(true);
            }
            viewHodler.c_homescene_gridViewItem_switch.setTag(position);
            viewHodler.c_homescene_gridViewItem_switch.setSwitchChangerListener(new SwitchButton.switchChangeListener() {
                @Override
                public void change(Boolean sta, String text, SwitchButton vi) {
                    int pos= (int) vi.getTag();
                    if (sta){
                        list.get(pos).setToState(0);
                    }
                    else {
                        list.get(pos).setToState(1);
                    }
                }
            });
        }
        //添加设备选项
        else {
            viewHodler.c_homescene_gridViewItem_image.setImageResource(R.mipmap.addq);
            viewHodler.c_homescene_gridViewItem_switch.setVisibility(View.GONE);
            viewHodler.c_homescene_gridViewItem_textView.setText("添加");
        }
        return convertView;
    }

    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.c_homescene_gridViewItem_image)RoundImageView c_homescene_gridViewItem_image;
        @BindView(R.id.c_homescene_gridViewItem_textView)TextView c_homescene_gridViewItem_textView;
        @BindView(R.id.c_homescene_gridViewItem_layout)RelativeLayout c_homescene_gridViewItem_layout;
        @BindView(R.id.c_homescene_gridViewItem_switch)SwitchButton c_homescene_gridViewItem_switch;//设备开关按钮

    }

}
