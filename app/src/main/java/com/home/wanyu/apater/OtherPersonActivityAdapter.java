package com.home.wanyu.apater;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.R;
import com.home.wanyu.adapter.MyCircleContactAdapterGridAdapter;
import com.home.wanyu.adapter.OtherPersonCircleAdapter;
import com.home.wanyu.bean.Bean_otherActivity;
import com.home.wanyu.bean.Bean_otherCircle;
import com.home.wanyu.lzhUtils.DataUtils;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/5/24.
 */

public class OtherPersonActivityAdapter extends BaseAdapter{
    List<Bean_otherActivity.RowsBean> listCircle;
    private Context context;
    public OtherPersonActivityAdapter(List<Bean_otherActivity.RowsBean> listCircle,Context context){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.otherperson_circle_item,null);
            hodler=new ViewHodler(convertView);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        Picasso.with(context).load(Ip.imagePath+listCircle.get(position).getAvatar()).error(R.mipmap.errorphoto).into(hodler.circle_head_img);
        hodler.circle_name_tv.setText(listCircle.get(position).getUser_name());
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


        hodler.circle_type_tv.setText(listCircle.get(position).getActivityTheme());

        hodler.circle_commend_msg.setText(listCircle.get(position).getActivityContent());
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
        String data= DataUtils.getData(mils);
        hodler.circle_time_tv.setText(data);

        hodler.circle_like_num.setText(listCircle.get(position).getLikeNum()+"");
        hodler.circle_commend_num.setText(listCircle.get(position).getParticipateNumber()+"");
        hodler.circle_like_img.setSelected(listCircle.get(position).isIslike());//是否已经点赞
//        String picture=listCircle.get(position).get;
//        if (!"".equals(picture)&&!TextUtils.isEmpty(picture)){
//            String[] pic = picture.split(",");
//            if (pic!=null&&pic.length>0){
//                MyCircleContactAdapterGridAdapter adapter=new MyCircleContactAdapterGridAdapter(pic,context);
//                hodler.circle_gridview_friend.setAdapter(adapter);
//            }
//        }
        return convertView;
    }
    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.other_circle_head_img_1)RoundImageView circle_head_img;//头像
        @BindView(R.id.other_circle_name_tv_1)TextView circle_name_tv;//姓名
        @BindView(R.id.other_circle_type_tv_1)TextView circle_type_tv;//标题
        @BindView(R.id.other_circle_commend_msg_1)TextView circle_commend_msg;//内容
        @BindView(R.id.other_circle_time_tv_1)TextView circle_time_tv;//时间

        @BindView(R.id.other_circle_like_num_1)TextView circle_like_num;//点赞数
        @BindView(R.id.other_circle_like_img_1)ImageView circle_like_img;//点赞的imageview
        @BindView(R.id.other_circle_commend_num_1)TextView circle_commend_num;//评论数
        @BindView(R.id.other_list_like_layout_1)LinearLayout list_like_layout;//点赞的layout
        @BindView(R.id.other_list_pinglun_layout_1)LinearLayout list_pinglun_layout;//评论的layout
        @BindView(R.id.other_circle_gridview_friend_1)MyGridView circle_gridview_friend;//图片
    }

}
