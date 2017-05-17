package com.home.wanyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.activity.MyCircleContactActivity;
import com.home.wanyu.lzhUtils.DataUtils;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.RoundImageView;

import java.text.SimpleDateFormat;
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
    List<Map<String,String>> listCircle;
    private Context context;
    public MyCircleContactAdapter_c(List<Map<String,String>> listCircle,Context context){
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
//        mp.put("select","0");//是否被选择
//        mp.put("islike","0");
//        mp.put("image",resId[i%resId.length]+"");//头像
//        mp.put("title","圈子标题"+i);
//        mp.put("data",data[i%data.length]);
//        mp.put("address","名流一品");
//        mp.put("name",name[i%name.length]);
//        mp.put("contact","今天天气很好"+i);
        if (MyCircleContactActivity.state==0){//当前显示的是编辑
            hodler.activity_my_circle_contact_list_layout.setVisibility(View.GONE);
        }
        else if (MyCircleContactActivity.state==1){
            hodler.activity_my_circle_contact_list_layout.setVisibility(View.VISIBLE);
        }
        hodler.activity_my_circle_contact_list_select.setTag(position);
        hodler.activity_my_circle_contact_list_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if (v.isSelected()){
                    listCircle.get(pos).put("select","0");
                }
                else {
                    listCircle.get(pos).put("select","1");
                }
                v.setSelected(!v.isSelected());
            }
        });
        hodler.circle_head_img.setImageResource(Integer.parseInt(listCircle.get(position).get("image")));
        hodler.circle_name_tv.setText(listCircle.get(position).get("name"));
        hodler.circle_type_tv.setText(listCircle.get(position).get("title"));
        hodler.circle_commend_msg.setText(listCircle.get(position).get("contact"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds;
        try{
            millionSeconds = sdf.parse(listCircle.get(position).get("data")).getTime();//毫秒
            hodler.circle_time_tv.setText(DataUtils.getData(millionSeconds));
        }
        catch (Exception e){
            millionSeconds=0;
            hodler.circle_time_tv.setText("未知");
        }
        hodler.circle_like_num.setText(listCircle.get(position).get("likenum"));
        hodler.circle_commend_num.setText(listCircle.get(position).get("pl"));
        if ("0".equals(listCircle.get(position).get("islike"))){
            hodler.circle_like_img.setSelected(false);
        }
        else if ("1".equals(listCircle.get(position).get("islike"))){
            hodler.circle_like_img.setSelected(true);
        }
        hodler.circle_like_img.setTag(position);
        hodler.circle_like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int pos= (int) v.getTag();
                if (v.isSelected()){
                    listCircle.get(pos).put("islike","0");
                }
                else {
                    listCircle.get(pos).put("islike","1");
                }
                v.setSelected(!v.isSelected());
            }
        });
        MyCircleContactAdapterGridAdapter adapter=new MyCircleContactAdapterGridAdapter(Integer.parseInt(listCircle.get(position).get("imageNum")),context);
        hodler.circle_gridview_friend.setAdapter(adapter);
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

    private void setAllSelect(int st) {//0全选，1取消全选
        if (listCircle!=null&&listCircle.size()>0){
            for (int i=0;i<listCircle.size();i++){
                listCircle.get(i).put("select","1");
            }
        }
    }
}
