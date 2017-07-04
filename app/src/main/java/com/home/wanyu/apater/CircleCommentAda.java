package com.home.wanyu.apater;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.activity.CircleCardMessageActivity;
import com.home.wanyu.bean.getCircleCommentMsg.Comment;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by liuhaidong on 2017/5/18.
 */

public class CircleCommentAda extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Comment> mCommentList = new ArrayList<>();
    private EditText mEdit;
    private TextView mCommentNum;
    private Myholder holder;
    private HttpTools mHttptools;
    private long coverPersonalId = 0;
    private long stateId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 115) {//获取评论列表
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getCircleCommentMsg.Root) {
                    com.home.wanyu.bean.getCircleCommentMsg.Root root = (com.home.wanyu.bean.getCircleCommentMsg.Root) o;
                    if (root.getCode().equals("0")) {
                        mCommentList = root.getResult().getComment();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "无法获取评论列表", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 116) {//提交评论接口
                Object o = msg.obj;
                if (o != null & o instanceof com.home.wanyu.bean.getCircleCommendResult.Root) {
                    com.home.wanyu.bean.getCircleCommendResult.Root root = (com.home.wanyu.bean.getCircleCommendResult.Root) o;

                    if (root.getCode().equals("0")) {
                        mEdit.setClickable(false);
                        mCommentNum.setText(Integer.valueOf(mCommentNum.getText().toString()) + 1 + "");
                        mEdit.setText("");
                        coverPersonalId = 0;
                        Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                        mHttptools.getCircleCommentList(mHandler, UserInfo.userToken, stateId);//获取评论列表接口

                    } else {
                        Toast.makeText(mContext, root.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    public CircleCommentAda(final Context mContext, List<Comment> mCommentList, final EditText mEdit, TextView mCommentNum, final long stateId) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
        this.mEdit = mEdit;
        this.mCommentNum = mCommentNum;
        this.stateId = stateId;
        this.mInflater = LayoutInflater.from(this.mContext);
        mHttptools = HttpTools.getHttpToolsInstance();
        //发送按钮
        this.mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        Toast.makeText(mContext, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    } else {
                        mEdit.setClickable(false);
                        Log.e("coverPersonalId=", coverPersonalId + "");
                        //提交评论接口
                        //  mHttptools.circleComment(mHandler, UserInfo.userToken, stateId, coverPersonalId, getEditContent());
                        AjaxParams ajaxParams = new AjaxParams();
                        ajaxParams.put("stateId", String.valueOf(stateId));
                        ajaxParams.put("coverPersonalId", String.valueOf(coverPersonalId));
                        ajaxParams.put("content", getEditContent());
                        ajaxParams.put("token", UserInfo.userToken);
                        mHttptools.circleComment(mHandler, ajaxParams);
                        //隐藏软键盘
                        ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }
                return false;
            }

        });


    }

    public void setmCommentList(List<Comment> mCommentList) {
        this.mCommentList = mCommentList;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new Myholder();
            convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
            holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
            holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
            holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
            holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
            convertView.setTag(holder);
        } else {
            holder = (Myholder) convertView.getTag();
        }

        //只是某个人评论的
        if (mCommentList.get(position).getCoverPersonalId() == 0) {
            holder.name1.setText(mCommentList.get(position).getUserName());//张三评论：
            holder.name2.setVisibility(View.GONE);
            holder.huifu.setVisibility(View.GONE);
            holder.msg.setText(":" + mCommentList.get(position).getContent());//你是个小猫咪


            //某个人回复某个人
        } else {
            holder.name1.setText(mCommentList.get(position).getUserName());//张三
            holder.name2.setVisibility(View.VISIBLE);
            holder.huifu.setVisibility(View.VISIBLE);//回复
            holder.name2.setText(mCommentList.get(position).getCoverPersonalName());//李四
            holder.msg.setText(":" + mCommentList.get(position).getContent());//我很好

        }

        holder.name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommentList.get(position).getPersonalId() == UserInfo.personalId) {
                    coverPersonalId = 0;
                    ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mEdit.setHint(R.string.comment_hint);
                } else {
                    coverPersonalId = mCommentList.get(position).getPersonalId();
                    ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mEdit.setHint("回复" + mCommentList.get(position).getUserName());
                }
            }
        });

        holder.name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommentList.get(position).getCoverPersonalId() == UserInfo.personalId) {
                    coverPersonalId = 0;
                    ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mEdit.setHint("说点什么");
                } else {
                    coverPersonalId = mCommentList.get(position).getCoverPersonalId();
                    ((InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mEdit.setHint("回复" + mCommentList.get(position).getCoverPersonalName());
                }
            }
        });


        return convertView;
    }

    class Myholder {
        TextView name1;
        TextView name2;
        TextView huifu;
        TextView msg;
    }


    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getEditContent() {
        String content = mEdit.getText().toString().trim();
        if (content.equals("")) {
            return "";
        }
        return mEdit.getText().toString().trim();
    }
}
