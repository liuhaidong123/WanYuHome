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
import com.home.wanyu.apater.HomeServiceAda;
import com.home.wanyu.bean.homeService.BusinessEntity;
import com.home.wanyu.bean.homeService.Menulist;
import com.home.wanyu.bean.homeService.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private MyListView myListView;
    private HomeServiceAda mAdapter;
    private List<Menulist> mList = new ArrayList<>();
    List<Menulist> changeList = new ArrayList<>();

    private ImageView mImg;
    private RatingBar mRatingBar;
    private TextView mPrice, mAddress,HomeService_name;
    private Button mAsk;
    private String telephone;
    private HttpTools mHttptools;
    private RelativeLayout mMore_ll;
    private ProgressBar mBAr;
    private ImageView mXia;
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
                        Picasso.with(HomeServiceActivity.this).load(UrlTools.BASE+businessEntity.getPicture()).resize(ImgUitls.getWith(HomeServiceActivity.this)/3,ImgUitls.getWith(HomeServiceActivity.this)/3).error(R.mipmap.error_big).into(
                                mImg
                        );
                        mRatingBar.setRating(businessEntity.getStar());
                        mPrice.setText("均价：¥"+businessEntity.getAverage()+"元");
                        mAddress.setText(businessEntity.getAddress());
                        HomeService_name.setText(businessEntity.getBusinessName());
                        telephone=businessEntity.getTelephone();
                    } else {
                        Picasso.with(HomeServiceActivity.this).load(UrlTools.BASE+businessEntity.getPicture()).resize(ImgUitls.getWith(HomeServiceActivity.this)/3,ImgUitls.getWith(HomeServiceActivity.this)/3).error(R.mipmap.error_big).into(
                                mImg
                        );
                        mRatingBar.setNumStars(0);
                        mPrice.setText("");
                        mAddress.setText("");
                        telephone="";
                    }


                    if (root.getResult().getMenulist()!=null){

                        mList=root.getResult().getMenulist();
                        for (int i=0;i<mList.size();i++){
                            if (i<=9){
                                changeList.add(mList.get(i));
                            }else {
                                break;
                            }
                        }
                        mAdapter.setmList(changeList);
                        mAdapter.notifyDataSetChanged();

                        if (changeList.size()==10){
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

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        mHttptools=HttpTools.getHttpToolsInstance();
        mHttptools.homeService(mHandler, UserInfo.userToken,11);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.HomeService_msg_back);
        mBack.setOnClickListener(this);
        myListView = (MyListView) findViewById(R.id.homeservice_listview);
        mAdapter = new HomeServiceAda(this,mList);
        myListView.setAdapter(mAdapter);
        mImg = (ImageView) findViewById(R.id.HomeService_img);
        mRatingBar = (RatingBar) findViewById(R.id.homeService_ratingBar);
        mPrice = (TextView) findViewById(R.id.decoration_msg_price);
        mAddress = (TextView) findViewById(R.id.Homeservice_address_TV);
        HomeService_name = (TextView) findViewById(R.id.HomeService_name);

        mAsk= (Button) findViewById(R.id.home_ask_btn);
        mAsk.setOnClickListener(this);

        mMore_ll= (RelativeLayout) findViewById(R.id.homeservice_more_rl);
        mMore_ll.setOnClickListener(this);
        mBAr= (ProgressBar) findViewById(R.id.homeservice_pbLocate);
        mXia= (ImageView) findViewById(R.id.homeservice_bottom_back);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }else if (id==mAsk.getId()){
            if (!telephone.equals("")){
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
