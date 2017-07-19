//package com.home.wanyu.apater;

//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.home.wanyu.HttpUtils.HttpTools;
//import com.home.wanyu.R;
//import com.home.wanyu.User.UserInfo;
//import com.home.wanyu.activity.CarPoolingCommentActivity;
//import com.home.wanyu.activity.CarPoolingMsgActivity;
//import com.home.wanyu.bean.carPoolingMsg.Commentlist;
//import com.home.wanyu.bean.carPoolingMsg.Root;
//import com.home.wanyu.bean.getCircleCommentMsg.Comment;
//
//import net.tsz.afinal.http.AjaxParams;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by liuhaidong on 2017/5/25.
 */

//public class CarPoolingCommentAda extends BaseAdapter {
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private List<Commentlist> mCommentList = new ArrayList<>();
//    private CarHolder holder;
//    private long coverPersonalId = 0;
//    private long carpoolingId;
//    private EditText mEdit_content;
//    private TextView mComment_Send_btn;
//    private LinearLayout mComment_ll, mEdit_ll;
//    private HttpTools mHttptools;
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 129) {//社区拼车评论
//                Object o = msg.obj;
//                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
//                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
//                    if (root.getCode().equals("0")) {
//                        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
//                        coverPersonalId = 0;
//                        mEdit_content.setText("");
//                        mEdit_content.setHint("评论...");
//                        if (carpoolingId != -1) {
//                            mHttptools.carPoolingMsg(mHandler, UserInfo.userToken, carpoolingId);//获取平车详情
//                        }
//                    } else {
//                        Toast.makeText(mContext, "评论失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            } else if (msg.what == 128) {//平车详情接口
//                Object o = msg.obj;
//                if (o != null && o instanceof Root) {
//                    Root root = (Root) o;
//                    if (root.getResult() != null && root.getResult().getCommentlist() != null) {
//
//                        mCommentList = root.getResult().getCommentlist();
//                        notifyDataSetChanged();
//                    }
//                }
//            }
//        }
//
//    } ;
//
//        public CarPoolingCommentAda(final Context mContext, List<Commentlist> mCommentList, EditText mEdit_content, TextView mComment_Send_btn, LinearLayout mComment_ll, LinearLayout mEdit_ll) {
//            this.mContext = mContext;
//            this.mCommentList = mCommentList;
//            this.mEdit_content = mEdit_content;
//            this.mComment_Send_btn = mComment_Send_btn;
//            this.mComment_ll = mComment_ll;
//            this.mEdit_ll = mEdit_ll;
//            this.mInflater = LayoutInflater.from(this.mContext);
//            mHttptools = HttpTools.getHttpToolsInstance();
//
//            this.mComment_Send_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (getEditContent().equals("")) {
//                        Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        AjaxParams ajax = new AjaxParams();
//                        ajax.put("token", UserInfo.userToken);
//                        ajax.put("carpoolingId", carpoolingId + "");
//                        ajax.put("coverPersonalId", coverPersonalId + "");
//                        ajax.put("content", getEditContent());
//
//                        Log.e("carpoolingId", carpoolingId + "");
//                        Log.e("coverPersonalId", coverPersonalId + "");
//                        mHttptools.carPoolingComment(mHandler, ajax);
//                        //隐藏软键盘
//                        ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }
//            });
//        }
//
//
//        public void setmCommentList(List<Commentlist> mCommentList, long carpoolingId) {
//            this.mCommentList = mCommentList;
//            this.carpoolingId = carpoolingId;
//        }
//
//        @Override
//        public int getCount() {
//            return mCommentList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//
//            if (convertView == null) {
//                holder = new CarHolder();
//                convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
//                holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
//                holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
//                holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
//                holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
//                convertView.setTag(holder);
//            } else {
//                holder = (CarHolder) convertView.getTag();
//            }
//
//            //只是当前用户评论的
//            if (mCommentList.get(position).getCoverPersonalId() == 0) {
//                holder.name1.setText(mCommentList.get(position).getUserName());//张三评论：
//                holder.name2.setVisibility(View.GONE);
//                holder.huifu.setVisibility(View.GONE);
//                holder.msg.setText(":" + mCommentList.get(position).getContent());//你是个小猫咪
//
//
//                //当前用户回复某个人
//            } else {
//                holder.name1.setText(mCommentList.get(position).getUserName());//张三
//                holder.name2.setVisibility(View.VISIBLE);
//                holder.huifu.setVisibility(View.VISIBLE);//回复
//                holder.name2.setText(mCommentList.get(position).getCoverPersonalName());//李四
//                holder.msg.setText(":" + mCommentList.get(position).getContent());//你是个傻货
//
//            }
//            holder.name1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mEdit_ll.setVisibility(View.VISIBLE);
//                    mComment_ll.setVisibility(View.GONE);
//                    //如果点击的用户名是当前用户的
//                    if (mCommentList.get(position).getPersonalId() == UserInfo.personalId) {
//                        coverPersonalId = 0;
//                        mEdit_content.setHint("评论...");
//                    } else {
//                        coverPersonalId = mCommentList.get(position).getPersonalId();
//                        mEdit_content.setHint("说点什么");
//                    }
//                }
//            });
//
//            holder.name2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mEdit_ll.setVisibility(View.VISIBLE);
//                    mComment_ll.setVisibility(View.GONE);
//                    if (mCommentList.get(position).getCoverPersonalId() == UserInfo.personalId) {
//                        coverPersonalId = 0;
//                        mEdit_content.setHint("评论...");
//                    } else {
//                        coverPersonalId = mCommentList.get(position).getCoverPersonalId();
//                        mEdit_content.setText( "回复" + mCommentList.get(position).getCoverPersonalName());
//                    }
//                }
//            });
//
//
//            return convertView;
//        }
//
//
//        /**
//         * 获取输入框的内容
//         *
//         * @return
//         */
//        public String getEditContent() {
//            String content = mEdit_content.getText().toString().trim();
//            if (content.equals("")) {
//                return "";
//            }
//            return mEdit_content.getText().toString().trim();
//        }
//
//        class CarHolder {
//            TextView name1;
//            TextView name2;
//            TextView huifu;
//            TextView msg;
//        }
   // }
