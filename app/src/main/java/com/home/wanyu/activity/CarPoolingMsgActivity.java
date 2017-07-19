package com.home.wanyu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.carPoolingMsg.CarpoolingEntity;
import com.home.wanyu.bean.carPoolingMsg.Commentlist;
import com.home.wanyu.bean.carPoolingMsg.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

public class CarPoolingMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private LinearLayout mCar_msg_comment_ll;
    private TextView mName, mDate, mState, mShen, mStart_time, mStart_address, mEnd_address, mJoin_num;
    private RoundImageView mHead;
    private LinearLayout mJieDan_ll, mJoin_ll;
    private ImageView mJoin_img;
    private int over;
    private long carpoolingId;
    private long coverPersonalId = 0;
    private CarpoolingEntity mCarpoolingEntity;
    private com.home.wanyu.bean.carPoolingMsg.Result result;
    private MyListView myListView;
    private CarPoolingCommentAda mAdapter;
    private List<Commentlist> mCommentList = new ArrayList<>();
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 128) {//平车详情接口
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null && root.getResult().getCommentlist() != null) {
                        mAll_rl.setVisibility(View.VISIBLE);
                        mNoData_rl.setVisibility(View.GONE);
                        result = root.getResult();
                        mCommentList = root.getResult().getCommentlist();

                        mAdapter.setmCommentList(mCommentList);
                        mAdapter.notifyDataSetChanged();

                        if (root.getResult().getCarpoolingEntity() != null) {
                            mCarpoolingEntity = root.getResult().getCarpoolingEntity();
                            Picasso.with(CarPoolingMsgActivity.this).load(UrlTools.BASE + root.getResult().getCarpoolingEntity().getAvatar()).resize(ImgUitls.getWith(CarPoolingMsgActivity.this) / 3, ImgUitls.getWith(CarPoolingMsgActivity.this) / 3).error(R.mipmap.error_small).into(mHead);
                            mName.setText(root.getResult().getCarpoolingEntity().getUser_name());
                            mDate.setText(root.getResult().getCarpoolingEntity().getCreateTimeString());
                            mStart_time.setText(root.getResult().getCarpoolingEntity().getDepartureTimeString());
                            mStart_address.setText(root.getResult().getCarpoolingEntity().getDeparturePlace());
                            mEnd_address.setText(root.getResult().getCarpoolingEntity().getEnd());

                            if (root.getResult().getCarpoolingEntity().getOver() == 1) {
                                over = 1;
                                mState.setText("正在进行");
                            } else if (root.getResult().getCarpoolingEntity().getOver() == 2) {
                                over = 2;
                                mState.setText("已结束");
                            } else {
                                mState.setText("");
                            }


                            if (root.getResult().getCarpoolingEntity().getCtype() == 1) {

                                mShen.setText("乘客");
                                mJieDan_ll.setVisibility(View.VISIBLE);
                                mJoin_ll.setVisibility(View.GONE);
                                if (root.getResult().getCarpoolingEntity().isOrders()) {
                                    mJieDan_ll.setBackgroundResource(R.color.repair_line);
                                } else {
                                    mJieDan_ll.setBackgroundResource(R.color.eac6);

                                }
                            } else if (root.getResult().getCarpoolingEntity().getCtype() == 2) {
                                RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) mAdd_Icon.getLayoutParams();
                                imgParams.setMargins(dip2px(CarPoolingMsgActivity.this, 10), dip2px(CarPoolingMsgActivity.this, 10), dip2px(CarPoolingMsgActivity.this, 10), dip2px(CarPoolingMsgActivity.this, 10));
                                mAdd_Icon.setLayoutParams(imgParams);
                                mAdd_Icon.setImageResource(R.mipmap.community_blue_add);
                                //估计这里会有问题，测试的时候再说
                                mHead_Gridview.setTop(dip2px(CarPoolingMsgActivity.this, 10));
                                mHead_Gridview.setBottom(dip2px(CarPoolingMsgActivity.this, 10));
                                mHead_Gridview.setLeft(dip2px(CarPoolingMsgActivity.this, 10));
                                mHead_Gridview.setRight(dip2px(CarPoolingMsgActivity.this, 10));
                                mShen.setText("司机");
                                mJieDan_ll.setVisibility(View.GONE);
                                mJoin_ll.setVisibility(View.VISIBLE);
                                mJoin_num.setText(root.getResult().getCarpoolingEntity().getParticipateNumber() + "人参加");

                                if (root.getResult().getIsLike()) {
                                    mJoin_img.setImageResource(R.mipmap.community_add);
                                } else {
                                    mJoin_img.setImageResource(R.mipmap.community_add_no);
                                }

                            }
                        }
                    } else {
                        mAll_rl.setVisibility(View.GONE);
                        mNoData_rl.setVisibility(View.VISIBLE);
                    }

                }
            } else if (msg.what == 130) {//加入拼车
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "加入拼车成功", Toast.LENGTH_SHORT).show();
                        //新增头像之后，在加入拼车成功之后，需要将头像添加一个（GridView中新增一条数据）
                        if (result != null) {
                            result.setIsLike(true);
                            mCarpoolingEntity.setParticipateNumber(mCarpoolingEntity.getParticipateNumber() + 1);
                            mJoin_num.setText(mCarpoolingEntity.getParticipateNumber() + "人参加");

                            if (result.getIsLike()) {
                                mJoin_img.setImageResource(R.mipmap.community_add);
                            } else {
                                mJoin_img.setImageResource(R.mipmap.community_add_no);
                            }
                        }

                    } else {
                        Toast.makeText(CarPoolingMsgActivity.this, "亲，您已经加入此拼车，不能重复加入了哦", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 131) {//接单
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "接单成功", Toast.LENGTH_SHORT).show();
                        if (mCarpoolingEntity != null) {
                            mCarpoolingEntity.setOrders(true);
                            if (mCarpoolingEntity.isOrders()) {
                                mJieDan_ll.setBackgroundResource(R.color.repair_line);
                            } else {
                                mJieDan_ll.setBackgroundResource(R.color.eac6);
                            }
                        }

                    } else {
                        Toast.makeText(CarPoolingMsgActivity.this, "亲，您已经接过此单子了哦", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 129) {//社区拼车评论
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "评论成功", Toast.LENGTH_SHORT).show();


                        if (carpoolingId != -1) {
                            mHttptools.carPoolingMsg(mHandler, UserInfo.userToken, carpoolingId);//获取平车详情
                        }
                    } else {
                        Toast.makeText(CarPoolingMsgActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private RelativeLayout mAll_rl, mNoData_rl;
    private ImageView mAdd_Icon;
    private MyGridView mHead_Gridview;
    private LinearLayout mComment_ll, mEdit_ll;
    private EditText mEdit_content;
    private TextView mComment_Send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling_msg);
        mHttptools = HttpTools.getHttpToolsInstance();
        //拼车ID carpoolingId
        carpoolingId = getIntent().getLongExtra("carpoolingId", -1);
        initView();
    }

    private void initView() {
        //软键盘发送按钮
        mEdit_content = (EditText) findViewById(R.id.edit);
        //新增的发送按钮
        mComment_Send_btn = (TextView) findViewById(R.id.comment_send_btn);
        mComment_Send_btn.setOnClickListener(this);
        //发送按钮
        mEdit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    } else {
                        AjaxParams ajax = new AjaxParams();
                        ajax.put("token", UserInfo.userToken);
                        ajax.put("carpoolingId", carpoolingId + "");
                        ajax.put("coverPersonalId", coverPersonalId + "");
                        ajax.put("content", getEditContent());

                        Log.e("carpoolingId", carpoolingId + "");
                        Log.e("coverPersonalId", coverPersonalId + "");
                        mHttptools.carPoolingComment(mHandler, ajax);
                        coverPersonalId=0;
                        mEdit_content.setText("");
                        mEdit_content.setHint("评论...");
                        //隐藏软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }
                return false;
            }

        });
        //所有布局
        mAll_rl = (RelativeLayout) findViewById(R.id.all_rl);
        //没有数据布局
        mNoData_rl = (RelativeLayout) findViewById(R.id.no_data_rl);
        //加入拼车人员add图标
        mAdd_Icon = (ImageView) findViewById(R.id.add_icon);
        //司机发布后，加入拼车的头像GridView
        mHead_Gridview = (MyGridView) findViewById(R.id.community_join_person_head);
        //返回
        mback = (ImageView) findViewById(R.id.car_msg_back);
        mback.setOnClickListener(this);
        //底部评论按钮全部布局（包括评论按钮、参加按钮、接单按钮）
        mComment_ll = (LinearLayout) findViewById(R.id.car_msg_btn);
        //输入评论内容布局(输入框和发送按钮)
        mEdit_ll = (LinearLayout) findViewById(R.id.card_comment_box);
        //评论按钮
        mCar_msg_comment_ll = (LinearLayout) findViewById(R.id.car_msg_comment_ll);
        mCar_msg_comment_ll.setOnClickListener(this);
        //接单按钮，加入按钮
        mJieDan_ll = (LinearLayout) findViewById(R.id.car_msg_jiedan);
        mJoin_ll = (LinearLayout) findViewById(R.id.car_msg_join_ll);
        mJoin_ll.setOnClickListener(this);
        mJieDan_ll.setOnClickListener(this);
        //加入图标
        mJoin_img = (ImageView) findViewById(R.id.join_img_car);
        //评论列表
        myListView = (MyListView) findViewById(R.id.car_msg_commend_listview);
        mAdapter = new CarPoolingCommentAda(this, mCommentList);
        myListView.setAdapter(mAdapter);

        //拼车帖子的一些信息
        mHead = (RoundImageView) findViewById(R.id.car_msg_head_img);
        mName = (TextView) findViewById(R.id.car_msg_name);
        mDate = (TextView) findViewById(R.id.car_msg_time);
        mState = (TextView) findViewById(R.id.car_msg_state);
        mShen = (TextView) findViewById(R.id.car_shen_msg);
        mStart_time = (TextView) findViewById(R.id.car_time_msg);
        mStart_address = (TextView) findViewById(R.id.car_start_address_msg);
        mEnd_address = (TextView) findViewById(R.id.car_end_address_msg);
        mJoin_num = (TextView) findViewById(R.id.join_num_car);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
            //评论按钮
        } else if (id == mCar_msg_comment_ll.getId()) {
            mComment_ll.setVisibility(View.GONE);
            mEdit_ll.setVisibility(View.VISIBLE);
        } else if (id == mJoin_ll.getId()) {//加入拼车
            if (mCarpoolingEntity != null) {
                if (over == 2) {
                    Toast.makeText(this, "请，此拼车活动已结束了哦", Toast.LENGTH_SHORT).show();
                } else {
                    if (mCarpoolingEntity.islike()) {
                        Toast.makeText(this, "亲，您已经加入此拼车，不能重复加入了哦", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mCarpoolingEntity.getParticipateNumber() < mCarpoolingEntity.getCnumber()) {
                            mHttptools.carPoolingJoin(mHandler, UserInfo.userToken, carpoolingId);
                        } else {
                            Toast.makeText(this, "亲，人数已满，不能继续添加了哦", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } else if (id == mJieDan_ll.getId()) {//接单
            if (over == 2) {
                Toast.makeText(this, "亲，此拼车活动已结束了哦", Toast.LENGTH_SHORT).show();
            } else {
                if (mCarpoolingEntity != null) {
                    if (mCarpoolingEntity.isOrders()) {
                        Toast.makeText(this, "亲，此单子已经被接过了哦", Toast.LENGTH_SHORT).show();
                    } else {
                        mHttptools.carPoolingOrder(mHandler, UserInfo.userToken, carpoolingId);

                    }
                }

            }
        } else if (id == mComment_Send_btn.getId()) {
            if (getEditContent().equals("")) {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            } else {
                AjaxParams ajax = new AjaxParams();
                ajax.put("token", UserInfo.userToken);
                ajax.put("carpoolingId", carpoolingId + "");
                ajax.put("coverPersonalId", coverPersonalId + "");
                ajax.put("content", getEditContent());

                Log.e("carpoolingId", carpoolingId + "");
                Log.e("coverPersonalId", coverPersonalId + "");
                mHttptools.carPoolingComment(mHandler, ajax);
                coverPersonalId=0;
                mEdit_content.setText("");
                mEdit_content.setHint("评论...");
                //隐藏软键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 触摸屏幕隐藏输入框布局，显示评论参加或接单布局
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mComment_ll.setVisibility(View.VISIBLE);
                mEdit_ll.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getEditContent() {
        String content = mEdit_content.getText().toString().trim();
        if (content.equals("")) {
            return "";
        }
        return mEdit_content.getText().toString().trim();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (carpoolingId != -1) {
            mHttptools.carPoolingMsg(mHandler, UserInfo.userToken, carpoolingId);//获取平车详情
        }
    }

    /**
     * 评论列表适配器
     */
    class CarPoolingCommentAda extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<Commentlist> mCommentList2 = new ArrayList<>();
        private CarHolder holder;

        public CarPoolingCommentAda(Context mContext, List<Commentlist> mCommentList) {
            this.mContext = mContext;
            this.mCommentList2 = mCommentList;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        public void setmCommentList(List<Commentlist> mCommentList) {
            this.mCommentList2 = mCommentList;
        }

        @Override
        public int getCount() {
            return mCommentList2.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new CarHolder();
                convertView = mInflater.inflate(R.layout.circle_commend_in_item, null);
                holder.name1 = (TextView) convertView.findViewById(R.id.in_name_tv_one);
                holder.name2 = (TextView) convertView.findViewById(R.id.in_name_tv_two);
                holder.huifu = (TextView) convertView.findViewById(R.id.in_huifu);
                holder.msg = (TextView) convertView.findViewById(R.id.in_name_commend_msg);
                convertView.setTag(holder);
            } else {
                holder = (CarHolder) convertView.getTag();
            }
            //只是当前用户评论的
            if (mCommentList2.get(position).getCoverPersonalId() == 0) {
                holder.name1.setText(mCommentList2.get(position).getUserName());//张三评论：
                holder.name2.setVisibility(View.GONE);
                holder.huifu.setVisibility(View.GONE);
                holder.msg.setText(":" + mCommentList2.get(position).getContent());//你是个小猫咪


                //当前用户回复某个人
            } else {
                holder.name1.setText(mCommentList2.get(position).getUserName());//张三
                holder.name2.setVisibility(View.VISIBLE);
                holder.huifu.setVisibility(View.VISIBLE);//回复
                holder.name2.setText(mCommentList2.get(position).getCoverPersonalName());//李四
                holder.msg.setText(":" + mCommentList2.get(position).getContent());//你是个傻货

            }

            holder.name1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEdit_ll.setVisibility(View.VISIBLE);
                    mComment_ll.setVisibility(View.GONE);
                    //如果点击的用户名是当前用户的
                    if (mCommentList2.get(position).getPersonalId() == UserInfo.personalId) {
                        coverPersonalId = 0;
                        mEdit_content.setHint("评论...");
                    } else {
                        coverPersonalId = mCommentList2.get(position).getPersonalId();
                        mEdit_content.setHint( "回复" + mCommentList2.get(position).getUserName());
                    }
                }
            });

            holder.name2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEdit_ll.setVisibility(View.VISIBLE);
                    mComment_ll.setVisibility(View.GONE);
                    if (mCommentList2.get(position).getCoverPersonalId() == UserInfo.personalId) {
                        coverPersonalId = 0;
                        mEdit_content.setHint("评论...");
                    } else {
                        coverPersonalId = mCommentList2.get(position).getCoverPersonalId();
                        mEdit_content.setHint( "回复" + mCommentList2.get(position).getCoverPersonalName());
                    }
                }
            });

            return convertView;
        }

        class CarHolder {
            TextView name1;
            TextView name2;
            TextView huifu;
            TextView msg;
        }
    }
}
