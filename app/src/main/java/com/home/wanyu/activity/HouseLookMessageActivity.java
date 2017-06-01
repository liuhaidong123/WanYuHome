package com.home.wanyu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.HouseLookConfigureAda;
import com.home.wanyu.apater.HouseViewpagerAda;
import com.home.wanyu.bean.HouseLookConfigure;
import com.home.wanyu.bean.HouseMsg.Root;
import com.home.wanyu.myUtils.TimeUtils;
import com.home.wanyu.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class HouseLookMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ViewPager mViewpager;
    private HouseViewpagerAda mViewPagerAda;
    private List<String> mImgList = new ArrayList<>();
    private TextView mImg_num;

    private MyGridView myGridView;
    private HouseLookConfigureAda lookConfigureAda;
    private List<HouseLookConfigure> mList = new ArrayList<>();

    private TextView mName, mTime, mMoney, mHomeType, mM_Size, mCeng, mDecition;

    private Button mCall_btn;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private View view;
    private TextView mPrompt, mThink, mSure_Call;
    private int callNum = -1;//0代表还有6次机会，6代表一次机会也没了
    private long telephone = -1;
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 147) {
                Object o = msg.obj;

                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        mName.setText(root.getResult().getResidentialQuarters());
                        mTime.setText(TimeUtils.getTime(root.getResult().getCreateTimeString()));
                        mMoney.setText(root.getResult().getRent() + "元/月");
                        mHomeType.setText(root.getResult().getApartmentLayout());
                        mM_Size.setText(root.getResult().getHousingArea() + "㎡");
                        mCeng.setText(root.getResult().getFloor() + "层/共" + root.getResult().getFloord()+"层");
                        mDecition.setText(root.getResult().getDirection());
                        callNum = 6 - root.getRecordnumber();
                        telephone = root.getResult().getTelephone();
                        //轮播图图片
                        if (!root.getResult().getPicture().equals("")) {
                            String[] strImg = root.getResult().getPicture().split(";");
                            for (int i = 0; i < strImg.length; i++) {
                                mImgList.add(strImg[i]);
                                mViewPagerAda = new HouseViewpagerAda(mImgList, HouseLookMessageActivity.this);
                                mViewpager.setAdapter(mViewPagerAda);
                            }

                            if (mImgList.size() == 1) {
                                mImg_num.setText("");
                            } else {
                                mImg_num.setText("1/" + mImgList.size());
                            }
                        } else {
                            Toast.makeText(HouseLookMessageActivity.this, "轮播图片为0", Toast.LENGTH_SHORT).show();

                        }

                        //获取配置信息
                        if (!root.getResult().getHouseAllocation().equals("")) {
                            String[] strConfigure = root.getResult().getHouseAllocation().split("；");
                            if (mList.size() != 0) {
                                for (int i = 0; i < strConfigure.length; i++) {
                                    for (int j = 0; j < mList.size(); j++) {
                                        if (strConfigure[i].equals(mList.get(j).getName())) {
                                            mList.get(j).setFlag(true);
                                        }
                                    }
                                }
                                lookConfigureAda.setList(mList);
                                lookConfigureAda.notifyDataSetChanged();
                            } else {
                                Toast.makeText(HouseLookMessageActivity.this, "获取房屋配置信息错误", Toast.LENGTH_SHORT).show();
                            }

                        }


                    } else {
                        Toast.makeText(HouseLookMessageActivity.this, "获取房屋信息失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if (msg.what==148){//打电话

                Object o=msg.obj;
                if (o!=null && o instanceof  com.home.wanyu.bean.getAreaActivityLike.Root){
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")){
                        callNum-=1;
                    }else {

                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_look_message);
        mHttptools = HttpTools.getHttpToolsInstance();
        //租房信息详情
        mHttptools.getHouseMsg(handler, UserInfo.userToken, getIntent().getLongExtra("id", -1));
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_look_back);
        mBack.setOnClickListener(this);

        //轮播图
        mViewpager = (ViewPager) findViewById(R.id.look_msg_viewpager);
        mImg_num = (TextView) findViewById(R.id.img_num);
        //viewpager滑动监听
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mImg_num.setText(position + 1 + "/" + mImgList.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        myGridView = (MyGridView) findViewById(R.id.look_configure_gridview);
        mList.add(new HouseLookConfigure("电视", R.mipmap.television_no, 1, false));
        mList.add(new HouseLookConfigure("宽带", R.mipmap.broadband_no, 2, false));
        mList.add(new HouseLookConfigure("洗衣机", R.mipmap.washer_no, 3, false));
        mList.add(new HouseLookConfigure("床", R.mipmap.bed_no, 4, false));
        mList.add(new HouseLookConfigure("热水器", R.mipmap.heater_no, 5, false));
        mList.add(new HouseLookConfigure("供暖", R.mipmap.heating_no, 6, false));
        mList.add(new HouseLookConfigure("柜子", R.mipmap.cabinet_no, 7, false));
        mList.add(new HouseLookConfigure("沙发", R.mipmap.sofa_no, 8, false));
        mList.add(new HouseLookConfigure("冰箱", R.mipmap.fridge_no, 9, false));
        mList.add(new HouseLookConfigure("空调", R.mipmap.air_conditioner_no, 10, false));
        lookConfigureAda = new HouseLookConfigureAda(this, mList);
        myGridView.setAdapter(lookConfigureAda);

        //预约
        mCall_btn = (Button) findViewById(R.id.look_house_btn);
        mCall_btn.setOnClickListener(this);
        view = LayoutInflater.from(this).inflate(R.layout.look_house_call_alert, null);
        mPrompt = (TextView) view.findViewById(R.id.house_msg);
        mThink = (TextView) view.findViewById(R.id.house_cancle);
        mThink.setOnClickListener(this);
        mSure_Call = (TextView) view.findViewById(R.id.house_sure);
        mSure_Call.setOnClickListener(this);
        builder = new AlertDialog.Builder(this);
        alert = builder.create();
        alert.setView(view);

        mName = (TextView) findViewById(R.id.look_area_name);
        mTime = (TextView) findViewById(R.id.look_area_time);
        mMoney = (TextView) findViewById(R.id.look_price_msg);
        mHomeType = (TextView) findViewById(R.id.look_type_msg);
        mM_Size = (TextView) findViewById(R.id.look_m_msg);
        mCeng = (TextView) findViewById(R.id.looh_ceng_msg);
        mDecition = (TextView) findViewById(R.id.looh_direction_msg);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
            //预约
        } else if (id == mCall_btn.getId()) {
            if (callNum == -1) {
                Toast.makeText(this, "无法预约错误", Toast.LENGTH_SHORT).show();
            } else {
                if (callNum == 0) {
                    Toast.makeText(this, "您本日已经没有查看房源机会，请明天联系房东", Toast.LENGTH_SHORT).show();
                } else {
                    mPrompt.setText("您本日还有" + callNum + "次查看房源机会，联系房东将会扣除1次，确认联系房东。");
                    alert.show();
                }

            }


        } else if (id == mSure_Call.getId()) {

            if (telephone != -1) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + telephone);
                intent.setData(data);
                startActivity(intent);
                alert.dismiss();
                //打电话接口
                mHttptools.houseCallPhone(handler,UserInfo.userToken,getIntent().getLongExtra("id", -1));
            }

        } else if (id == mThink.getId()) {
            alert.dismiss();
        }
    }
}
