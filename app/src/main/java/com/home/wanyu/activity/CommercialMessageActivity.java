package com.home.wanyu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CommercialOthersGoodsAda;
import com.home.wanyu.bean.homeService.BusinessEntity;
import com.home.wanyu.bean.homeService.Menulist;
import com.home.wanyu.bean.homeService.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommercialMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mback;
    private MyListView myListView;
    private CommercialOthersGoodsAda mAdapter;

    private List<Menulist> mList = new ArrayList<>();
    List<Menulist> changeList = new ArrayList<>();
    private ImageView mImg, mImg_phone;
    private RatingBar mRatingBar;
    private TextView mPrice, mAddress, HomeService_name, mTitle_shop_name;
    private HttpTools mHttptools;
    private RelativeLayout mMore_ll;
    private ProgressBar mBAr;
    private ImageView mXia;
    private String telephone="";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 133) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    BusinessEntity businessEntity = root.getResult().getBusinessEntity();
                    if (businessEntity != null) {
                        Picasso.with(CommercialMessageActivity.this).load(UrlTools.BASE + businessEntity.getPicture()).resize(ImgUitls.getWith(CommercialMessageActivity.this) / 3, ImgUitls.getWith(CommercialMessageActivity.this) / 3).error(R.mipmap.error_big).into(
                                mImg
                        );
                        mRatingBar.setRating(businessEntity.getStar());
                        mPrice.setText("均价：¥" + businessEntity.getAverage() + "元/人");
                        mAddress.setText(businessEntity.getAddress());
                        HomeService_name.setText(businessEntity.getBusinessName());
                        telephone = businessEntity.getTelephone();
                        mTitle_shop_name.setText(businessEntity.getBusinessName());
                    } else {
                        Picasso.with(CommercialMessageActivity.this).load(UrlTools.BASE + businessEntity.getPicture()).resize(ImgUitls.getWith(CommercialMessageActivity.this) / 3, ImgUitls.getWith(CommercialMessageActivity.this) / 3).error(R.mipmap.error_big).into(
                                mImg
                        );
                        mRatingBar.setNumStars(0);
                        mPrice.setText("");
                        mAddress.setText("");
                        telephone = "";
                        mTitle_shop_name.setText("");
                    }


                    if (root.getResult().getMenulist() != null) {

                        mList = root.getResult().getMenulist();
                        for (int i = 0; i < mList.size(); i++) {
                            if (i <= 9) {
                                changeList.add(mList.get(i));
                            } else {
                                break;
                            }
                        }
                        mAdapter.setmList(changeList);
                        mAdapter.notifyDataSetChanged();

                        if (changeList.size() == 10) {
                            mMore_ll.setVisibility(View.VISIBLE);
                            mBAr.setVisibility(View.INVISIBLE);
                            mXia.setVisibility(View.VISIBLE);
                        } else {
                            mMore_ll.setVisibility(View.GONE);
                            mBAr.setVisibility(View.INVISIBLE);
                            mXia.setVisibility(View.GONE);
                        }
                    }

                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_message);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.homeService(mHandler, UserInfo.userToken, getIntent().getLongExtra("businessId", -1));//获取商店详情
        initView();
    }

    private void initView() {
        mback = (ImageView) findViewById(R.id.commercial_msg_back);
        mback.setOnClickListener(this);

        myListView = (MyListView) findViewById(R.id.goods_others_listview);
        mAdapter = new CommercialOthersGoodsAda(this, mList);
        myListView.setAdapter(mAdapter);


        mImg = (ImageView) findViewById(R.id.goods_msg_img);
        mRatingBar = (RatingBar) findViewById(R.id.goods_msg_ratingBar);
        mPrice = (TextView) findViewById(R.id.goods_msg_price);
        mAddress = (TextView) findViewById(R.id.goods_msg_address_TV);
        HomeService_name = (TextView) findViewById(R.id.goods_msg_name);
        mTitle_shop_name = (TextView) findViewById(R.id.commercial_shop_name);
        mImg_phone = (ImageView) findViewById(R.id.goods_mmsg_phone);
        mImg_phone.setOnClickListener(this);

        mMore_ll = (RelativeLayout) findViewById(R.id.commercial_goods_more_rl);
        mMore_ll.setOnClickListener(this);
        mBAr = (ProgressBar) findViewById(R.id.goods_msg_pbLocate);
        mXia = (ImageView) findViewById(R.id.goods_bottom_back);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mImg_phone.getId()) {
            if (!"".equals(telephone)) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + telephone);
                intent.setData(data);
                startActivity(intent);
            }

        }else if (id==mMore_ll.getId()){//更多
            for (int i=mAdapter.getmList().size();i<mList.size();i++){
                if (i<=19){
                    changeList.add(mList.get(i));
                }else {
                    break;
                }
            }

            mAdapter.setmList(changeList);
            mAdapter.notifyDataSetChanged();

            if (changeList.size()%10==0){
                mMore_ll.setVisibility(View.VISIBLE);
                mBAr.setVisibility(View.INVISIBLE);
                mXia.setVisibility(View.VISIBLE);
            }else {
                mMore_ll.setVisibility(View.GONE);
                mBAr.setVisibility(View.INVISIBLE);
                mXia.setVisibility(View.GONE);
            }

        }
    }
}
