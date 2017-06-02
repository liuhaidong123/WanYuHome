package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CarPoolingCommentAda;
import com.home.wanyu.bean.carPoolingList.Result;
import com.home.wanyu.bean.carPoolingMsg.CarpoolingEntity;
import com.home.wanyu.bean.carPoolingMsg.Commentlist;
import com.home.wanyu.bean.carPoolingMsg.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarPoolingMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private LinearLayout mCar_msg_comment_ll;
    private TextView mName, mDate, mState, mShen, mStart_time, mStart_address, mEnd_address, mJoin_num;
    private RoundImageView mHead;
    private LinearLayout mCommemt_ll, mJieDan_ll, mJoin_ll;
    private ImageView mJoin_img;
    private int over;
    private long carpoolingId ;
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
                    if (root.getResult()!=null&&root.getResult().getCommentlist()!=null){
                        result=root.getResult();
                        mCommentList = root.getResult().getCommentlist();
                        mAdapter.setmCommentList(mCommentList, carpoolingId);
                        mAdapter.notifyDataSetChanged();

                        if (root.getResult().getCarpoolingEntity()!=null){
                            mCarpoolingEntity=root.getResult().getCarpoolingEntity();
                            Picasso.with(CarPoolingMsgActivity.this).load(UrlTools.BASE + root.getResult().getCarpoolingEntity().getAvatar()).resize(ImgUitls.getWith(CarPoolingMsgActivity.this) / 3, ImgUitls.getWith(CarPoolingMsgActivity.this) / 3).error(R.mipmap.error_small).into(mHead);
                            mName.setText(root.getResult().getCarpoolingEntity().getUser_name());
                            mDate.setText(root.getResult().getCarpoolingEntity().getCreateTimeString());
                            mStart_time.setText(root.getResult().getCarpoolingEntity().getDepartureTimeString());
                            mStart_address.setText(root.getResult().getCarpoolingEntity().getDeparturePlace());
                            mEnd_address.setText(root.getResult().getCarpoolingEntity().getEnd());

                            if (root.getResult().getCarpoolingEntity().getOver() == 1) {
                                over=1;
                                mState.setText("正在进行");
                            } else if (root.getResult().getCarpoolingEntity().getOver() == 2) {
                                over=2;
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
                                    mJieDan_ll.setBackgroundResource(R.color.bg_rect);

                                }
                            } else if (root.getResult().getCarpoolingEntity().getCtype() == 2) {
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
                    }

                }
            } else if (msg.what == 130) {//加入拼车
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "加入拼车成功", Toast.LENGTH_SHORT).show();
                        if (result!=null){
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
            }else if (msg.what == 131) {//接单
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingMsgActivity.this, "接单成功", Toast.LENGTH_SHORT).show();
                        if (mCarpoolingEntity!=null){
                            mCarpoolingEntity.setOrders(true);
                            if (mCarpoolingEntity.isOrders()) {
                                mJieDan_ll.setBackgroundResource(R.color.repair_line);
                            } else {
                                mJieDan_ll.setBackgroundResource(R.color.bg_rect);
                            }
                        }

                    } else {
                        Toast.makeText(CarPoolingMsgActivity.this, "亲，您已经接过此单子了哦", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling_msg);
        mHttptools = HttpTools.getHttpToolsInstance();
        //拼车ID
        carpoolingId=getIntent().getLongExtra("carpoolingId",-1);
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.car_msg_back);
        mback.setOnClickListener(this);

        myListView = (MyListView) findViewById(R.id.car_msg_commend_listview);
        mAdapter = new CarPoolingCommentAda(this, mCommentList);
        myListView.setAdapter(mAdapter);
        //评论
        mCar_msg_comment_ll = (LinearLayout) findViewById(R.id.car_msg_comment_ll);
        mCar_msg_comment_ll.setOnClickListener(this);

        mHead = (RoundImageView) findViewById(R.id.car_msg_head_img);
        mName = (TextView) findViewById(R.id.car_msg_name);
        mDate = (TextView) findViewById(R.id.car_msg_time);
        mState = (TextView) findViewById(R.id.car_msg_state);
        mShen = (TextView) findViewById(R.id.car_shen_msg);
        mStart_time = (TextView) findViewById(R.id.car_time_msg);
        mStart_address = (TextView) findViewById(R.id.car_start_address_msg);
        mEnd_address = (TextView) findViewById(R.id.car_end_address_msg);
        mJoin_num = (TextView) findViewById(R.id.join_num_car);

        mCommemt_ll = (LinearLayout) findViewById(R.id.car_msg_comment_ll);
        mJieDan_ll = (LinearLayout) findViewById(R.id.car_msg_jiedan);
        mJoin_ll = (LinearLayout) findViewById(R.id.car_msg_join_ll);
        mJoin_ll.setOnClickListener(this);
        mJieDan_ll.setOnClickListener(this);
        mCommemt_ll.setOnClickListener(this);
        mJoin_img = (ImageView) findViewById(R.id.join_img_car);


    }

    private long coverPersonalId = 0;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
            //评论
        } else if (id == mCar_msg_comment_ll.getId()) {
            Intent intent = new Intent(this, CarPoolingCommentActivity.class);
            intent.putExtra("flag", "carPooling");
            intent.putExtra("coverPersonalId", coverPersonalId);
            intent.putExtra("Id", carpoolingId);
            intent.putExtra("hint", "说点什么");
            startActivity(intent);
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
        }else if (id==mJieDan_ll.getId()){//接单
            if (over == 2) {
                Toast.makeText(this, "亲，此拼车活动已结束了哦", Toast.LENGTH_SHORT).show();
            } else {
                if (mCarpoolingEntity!=null){
                    if (mCarpoolingEntity.isOrders()) {
                        Toast.makeText(this, "亲，您已经接过此单子了哦", Toast.LENGTH_SHORT).show();
                    } else {
                        mHttptools.carPoolingOrder(mHandler, UserInfo.userToken, carpoolingId);
                    }
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (carpoolingId != -1) {
            mHttptools.carPoolingMsg(mHandler, UserInfo.userToken, carpoolingId);
        }
    }
}
