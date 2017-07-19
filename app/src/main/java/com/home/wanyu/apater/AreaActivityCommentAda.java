//package com.home.wanyu.apater;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.home.wanyu.R;
//import com.home.wanyu.User.UserInfo;
//import com.home.wanyu.activity.CarPoolingCommentActivity;
//import com.home.wanyu.bean.getAreaActivityMsg.Commentlist;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.content.Context.INPUT_METHOD_SERVICE;
//
///**
// * Created by liuhaidong on 2017/5/24.
// */
//
//public class AreaActivityCommentAda extends BaseAdapter {
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private List<Commentlist> list = new ArrayList<>();
//    private MyAreaHolder holder;
//    private long coverPersonalId = 0;
//    private long activityId;
//    public AreaActivityCommentAda(Context mContext, List<Commentlist> list, long activityId) {
//        this.mContext = mContext;
//        this.list = list;
//        this.activityId=activityId;
//        this.mInflater = LayoutInflater.from(this.mContext);
//    }
//
//    public void setList(List<Commentlist> list) {
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            holder = new MyAreaHolder();
//            convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
//            holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
//            holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
//            holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
//            holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
//            convertView.setTag(holder);
//        } else {
//            holder = (MyAreaHolder) convertView.getTag();
//        }
//        //只是当前用户评论的
//        if (list.get(position).getCoverPersonalId() == 0) {
//            holder.name1.setText(list.get(position).getUserName());//张三评论：
//            holder.name2.setVisibility(View.GONE);
//            holder.huifu.setVisibility(View.GONE);
//            holder.msg.setText(":" + list.get(position).getContent());//你是个小猫咪
//
//
//            //当前用户回复回复某个人
//        } else {
//            holder.name1.setText(list.get(position).getUserName());//张三
//            holder.name2.setVisibility(View.VISIBLE);
//            holder.huifu.setVisibility(View.VISIBLE);//回复
//            holder.name2.setText(list.get(position).getCoverPersonalName());//李四
//            holder.msg.setText(":" + list.get(position).getContent());//我很好
//
//        }
//        holder.name1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (list.get(position).getPersonalId() == UserInfo.personalId) {
//                    coverPersonalId = 0;
//                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
//                    intent.putExtra("flag","activity");
//                    intent.putExtra("coverPersonalId",coverPersonalId);
//                    intent.putExtra("Id",activityId);
//                    intent.putExtra("hint","说点什么");
//                    mContext.startActivity(intent);
//                } else {
//                    coverPersonalId = list.get(position).getPersonalId();
//                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
//                    intent.putExtra("flag","activity");
//                    intent.putExtra("coverPersonalId",coverPersonalId);
//                    intent.putExtra("Id",activityId);
//                    intent.putExtra("hint","回复" +list.get(position).getUserName());
//                    mContext.startActivity(intent);
//                }
//            }
//        });
//
//        holder.name2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (list.get(position).getCoverPersonalId() == UserInfo.personalId) {
//                    coverPersonalId = 0;
//                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
//                    intent.putExtra("flag","activity");
//                    intent.putExtra("coverPersonalId",coverPersonalId);
//                    intent.putExtra("Id",activityId);
//                    intent.putExtra("hint","说点什么");
//                    mContext.startActivity(intent);
//                } else {
//                    coverPersonalId = list.get(position).getCoverPersonalId();
//                    Intent intent=new Intent(mContext, CarPoolingCommentActivity.class);
//                    intent.putExtra("flag","activity");
//                    intent.putExtra("coverPersonalId",coverPersonalId);
//                    intent.putExtra("Id",activityId);
//                    intent.putExtra("hint","回复" +list.get(position).getCoverPersonalName());
//
//                    mContext.startActivity(intent);
//                }
//            }
//        });
//
//
//
//        return convertView;
//    }
//
//    class MyAreaHolder {
//        TextView name1;
//        TextView name2;
//        TextView huifu;
//        TextView msg;
//    }
//
//}
