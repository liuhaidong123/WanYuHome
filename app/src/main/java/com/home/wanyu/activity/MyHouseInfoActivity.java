package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.wheelView.MyAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter;
import com.home.wanyu.lzhView.wheelView.MyWheelAdapter50;
import com.home.wanyu.lzhView.wheelView.MyWheelArr50;
import com.home.wanyu.lzhView.wheelView.MyWheelViewAdapterArray;
import com.home.wanyu.lzhView.wheelView.OnWheelChangedListener;
import com.home.wanyu.lzhView.wheelView.WheelView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的房屋信息
public class MyHouseInfoActivity extends MyActivity {
    @BindView(R.id.my_address_name)EditText my_address_name;//姓名
    @BindView(R.id.my_address_phoneNum)EditText my_address_phoneNum;//电话
    @BindView(R.id.my_address_postCode)TextView my_address_postCode;//显示身份的view（业主，访客等）
    @BindView(R.id.my_address_addressSelect)TextView my_address_addressSelect;//显示选择的地址的view
    @BindView(R.id.my_address_addressinfo)EditText my_address_addressinfo;//用户填写的信息（街道，门牌号等）

    private PopupWindow popupW;
    private WheelView my_address_pop_wheelV_Provice,my_address_pop_wheelV_City,my_address_pop_wheelV_Areas;
    private List<String> mProvince;//省
    private Map<String,List<String>> mCity;
    private Map<String,List<String>>mArea;
    private List<String>listCity;
    private List<String>listArea;
    private String provice;
    private String city;
    private int currentPro,currentCity;
    private MyAdapter adapterPro,adapterCity,adapterAres;
    private TextView activity_address_title;

    private String selectAddress;//被选中等address（省市县）
    private String pro,cit,area;
    private String resultStr;
    private String id;

    String name;
    String addressSelect;
    String addressInfo;
    String postCode;
    String phonenum;

    private PopupWindow pop;
    @BindArray(R.array.addressStr)String[]addressStr;//存放 用户身份
    private int selectPosAdd=0;//选择的业主身份
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.activity_my_house_info_title);
        initChildView(R.layout.activity_my_house_info);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        pullXml();
    }
    public void Submit(View vi){
        mToast.Toast(con,"提交审核");
    }
    @OnClick({R.id.my_house_info_shenfen,R.id.my_address_layout_selectAddress})
    public void click(View view){
        switch (view.getId()){
            case R.id.my_house_info_shenfen://身份
                showwWindow();
                break;
            case R.id.my_address_layout_selectAddress://选择的地址
                showWindowSelect();
                break;
        }
    }

    private void showwWindow() {
        View vi=getLayoutInflater().inflate(R.layout.pop_address, null);
        TextView my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle_2);
        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
       TextView my_address_pop_submit_2= (TextView) vi.findViewById(R.id.my_address_pop_submit_2);

        my_address_pop_submit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_address_postCode.setText(addressStr[selectPosAdd]);
                pop.dismiss();
            }
        });
        WheelView my_address_wheel= (WheelView) vi.findViewById(R.id.my_address_wheel);
        List<String>li=new ArrayList<>();
        for (int i=0;i<addressStr.length;i++){
            li.add(addressStr[i]);
        }
        MyWheelAdapter50 adapter=new MyWheelAdapter50(MyHouseInfoActivity.this,li,"");
        my_address_wheel.setViewAdapter(adapter);
        my_address_wheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectPosAdd=newValue;
            }
        });
        pop=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(vi);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }


    @Override
    public void getSerVerData() {

    }



    //地址选择器
    private void showWindowSelect() {

        View vi=getLayoutInflater().inflate(R.layout.my_address_popup, null);
        my_address_pop_wheelV_Provice= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Provice);
        my_address_pop_wheelV_City= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_City);
        my_address_pop_wheelV_Areas= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Areas);


        adapterPro=new MyAdapter(MyHouseInfoActivity.this,mProvince);
        my_address_pop_wheelV_Provice.setViewAdapter(adapterPro);
        my_address_pop_wheelV_Provice.setVisibleItems(5);

        adapterCity=new MyAdapter(MyHouseInfoActivity.this,mCity.get(mProvince.get(0)));
        my_address_pop_wheelV_City.setViewAdapter(adapterCity);
        my_address_pop_wheelV_City.setVisibleItems(5);
        List<String> li=mCity.get(mProvince.get(0));
        for (int i=0;i<li.size();i++){
            Log.i("-------------",li.get(i));
        }
        adapterAres=new MyAdapter(MyHouseInfoActivity.this,mArea.get(li.get(0)));
        my_address_pop_wheelV_Areas.setViewAdapter(adapterAres);
        my_address_pop_wheelV_Areas.setVisibleItems(5);

        TextView  my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle);
        TextView    my_address_pop_submit= (TextView) vi.findViewById(R.id.my_address_pop_submit);

        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupW.dismiss();
            }
        });

        my_address_pop_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress=pro+" "+cit+" "+area;
                my_address_addressSelect.setText(selectAddress);
                popupW.dismiss();
            }
        });


        my_address_pop_wheelV_Provice.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentPro=newValue;
                my_address_pop_wheelV_City.setViewAdapter(new MyAdapter(MyHouseInfoActivity.this,mCity.get(mProvince.get(currentPro))));
                my_address_pop_wheelV_City.setCurrentItem(0);
                List<String>lit=mCity.get(mProvince.get(currentPro));
                my_address_pop_wheelV_Areas.setViewAdapter(new MyAdapter(MyHouseInfoActivity.this,mArea.get(lit.get(0))));
                my_address_pop_wheelV_Areas.setCurrentItem(0);

                pro=mProvince.get(newValue);
                cit=mCity.get(mProvince.get(newValue)).get(0);

                List<String>li=mCity.get(mProvince.get(currentPro));
                area=mArea.get(li.get(0)).get(0);

            }
        });
        my_address_pop_wheelV_City.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentCity=newValue;
                List<String>li=mCity.get(mProvince.get(currentPro));
                my_address_pop_wheelV_Areas.setViewAdapter(new MyAdapter(MyHouseInfoActivity.this,mArea.get(li.get(newValue))));
                my_address_pop_wheelV_Areas.setCurrentItem(0);

                cit=li.get(newValue);
                area=mArea.get(li.get(newValue)).get(0);
            }
        });

        my_address_pop_wheelV_Areas.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                List<String>li=mCity.get(mProvince.get(currentPro));
                area=mArea.get(li.get(currentCity)).get(newValue);
            }
        });

        popupW=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popupW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupW.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupW.setContentView(vi);
        popupW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popupW.setTouchable(true);
        popupW.setFocusable(true);
        popupW.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_my_house_info);
        popupW.setAnimationStyle(R.style.popup3_anim);
        popupW.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        popupW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }


    //解析数据
    public boolean pullXml(){
        try{
            InputStream is=getResources().getAssets().open("city.xml");

            //    创建XmlPullParserFactory解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //    通过XmlPullParserFactory工厂类实例化一个XmlPullParser解析类
            XmlPullParser parser = factory.newPullParser();
            //    根据指定的编码来解析xml文档
            parser.setInput(is, "utf-8");
            //    得到当前的事件类型
            int eventType = parser.getEventType();
            //    只要没有解析到xml的文档结束，就一直解析
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    //    解析到文档开始的时候
                    case XmlPullParser.START_DOCUMENT:
                        mProvince = new ArrayList<>();//省份集合
                        mCity=new HashMap<>();//市区集合
                        mArea=new HashMap<>();//县区集合
                        break;
                    //    解析到xml标签的时候
                    case XmlPullParser.START_TAG:
                        switch (parser.getName()){
                            case "province":
                                mProvince.add(parser.getAttributeValue(0));
                                listCity=new ArrayList<>();
                                provice=parser.getAttributeValue(0);
                                break;
                            case "city":
                                listCity.add(parser.getAttributeValue(0));
                                city=parser.getAttributeValue(0);
                                listArea=new ArrayList<>();
                                break;
                            case "area":
                                listArea.add(parser.getAttributeValue(0));
                                break;
                        }
                        break;
                    //    解析到xml标签结束的时候
                    case XmlPullParser.END_TAG:
                        switch (parser.getName()){
                            case "province":
                                mCity.put(provice,listCity);
                                break;
                            case "city":
                                mArea.put(city,listArea);
                                break;
                        }
                        break;
                }
                //    通过next()方法触发下一个事件
                eventType = parser.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
