package com.home.wanyu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.activity.MyCircleContactActivity;
import com.home.wanyu.bean.Bean_QZ;
import com.home.wanyu.lzhUtils.DataUtils;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/16.
 */
//我的圈子的适配器
public class MyCircleContactAdapter_c extends BaseAdapter{
    List<Bean_QZ.RowsBean>listCircle;
    private Context context;
    public MyCircleContactAdapter_c(List<Bean_QZ.RowsBean>listCircle,Context context){
        this.listCircle=listCircle;
        this.context=context;
    }
    @Override
    public int getCount() {
        return listCircle==null?0:listCircle.size();
    }

    @Override
    public Object getItem(int position) {
        return listCircle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.mycirclecontact_list_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }

        if (MyCircleContactActivity.state==0){//当前显示的是编辑
            hodler.activity_my_circle_contact_list_layout.setVisibility(View.GONE);
        }
        else if (MyCircleContactActivity.state==1){
            hodler.activity_my_circle_contact_list_layout.setVisibility(View.VISIBLE);
        }
        hodler.activity_my_circle_contact_list_select.setTag(position);
        if (listCircle.get(position).isSele()){
            hodler.activity_my_circle_contact_list_select.setSelected(true);
        }
        else {
            hodler.activity_my_circle_contact_list_select.setSelected(false);
        }
        hodler.activity_my_circle_contact_list_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if (v.isSelected()){//已经被选中
                    listCircle.get(pos).setSele(false);
                }
                else {//没有被选中
                    listCircle.get(pos).setSele(true);
                    }
                v.setSelected(!v.isSelected());
            }
        });
        Picasso.with(context).load(Ip.imagePath+listCircle.get(position).getAvatar()).error(R.mipmap.errorphoto).into(hodler.circle_head_img);
        hodler.circle_name_tv.setText(listCircle.get(position).getUserName());
//
//        |State          |Object   |发状态实体类
//        发状态实体类字段说明
//                |id             |Long      |Y    |编号
//                |content        |String    |Y    |内容
//                |picture        |String    |N    |图片--最多6张图片
//                |commentNum     |Integer   |N    |评论数
//                |likeNum        |Integer   |N    |点赞数
//                |personalId     |Long      |Y    |个人信息编号
//                |visibleRange   |Integer   |Y    |范围--"1=本小区的人可以看到2=其他小区都能看到"
//                |residentialQuartersId|Long      |Y    |小区编号--小区表
//                |createTime     |java.sql.Timestamp|Y    |发布时间
//                |categoryId     |Long      |Y    |分类编号


        hodler.circle_type_tv.setVisibility(View.GONE);//圈子没有标题

        hodler.circle_commend_msg.setText(listCircle.get(position).getContent());
        String time=listCircle.get(position).getCreateTimeString();//内容
//        2017-05-17 16:10:54
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long mils;
        try {
            mils=sdf.parse(listCircle.get(position).getCreateTimeString()).getTime();//毫秒
        } catch (ParseException e) {
            mils=0;
            e.printStackTrace();
        }
        String data=DataUtils.getData(mils);
        hodler.circle_time_tv.setText(data);

        hodler.circle_like_num.setText(listCircle.get(position).getLikeNum()+"");
        hodler.circle_commend_num.setText(listCircle.get(position).getCommentNum()+"");
        hodler.circle_like_img.setSelected(listCircle.get(position).isIslike());//是否已经点赞
        String picture=listCircle.get(position).getPicture();
        if (!"".equals(picture)&&!TextUtils.isEmpty(picture)){
            String[] pic = picture.split(",");
            if (pic!=null&&pic.length>0){
                List<String>li=new ArrayList<>();
                for (int i=0;i<pic.length;i++){
                    li.add(pic[i]);
                }
                MyCircleContactAdapterGridAdapter adapter=new MyCircleContactAdapterGridAdapter(li,context);
                hodler.circle_gridview_friend.setAdapter(adapter);
            }
        }
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.activity_my_circle_contact_list_layout)RelativeLayout activity_my_circle_contact_list_layout;//删除的view
        @BindView(R.id.activity_my_circle_contact_list_select)ImageView activity_my_circle_contact_list_select;//选择的按钮
        @BindView(R.id.circle_head_img_1)RoundImageView circle_head_img;//头像
        @BindView(R.id.circle_name_tv_1)TextView circle_name_tv;//姓名
        @BindView(R.id.circle_type_tv_1)TextView circle_type_tv;//标题
        @BindView(R.id.circle_commend_msg_1)TextView circle_commend_msg;//内容
        @BindView(R.id.circle_time_tv_1)TextView circle_time_tv;//时间

        @BindView(R.id.circle_like_num_1)TextView circle_like_num;//点赞数
        @BindView(R.id.circle_like_img_1)ImageView circle_like_img;//点赞的imageview
        @BindView(R.id.circle_commend_num_1)TextView circle_commend_num;//评论数
        @BindView(R.id.list_like_layout_1)LinearLayout list_like_layout;//点赞的layout
        @BindView(R.id.list_pinglun_layout_1)LinearLayout list_pinglun_layout;//评论的layout
        @BindView(R.id.circle_gridview_friend_1)MyGridView circle_gridview_friend;//图片
    }

}
