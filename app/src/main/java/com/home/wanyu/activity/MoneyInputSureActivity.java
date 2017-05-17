package com.home.wanyu.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;

public class MoneyInputSureActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack,mMoney_tell_img;
    private EditText mEdit_input;
    private TextView mMoney_unit,mMoney_num,mMoney_name,mMoney_address,mMoney_type,mMoney_many,mMoney_tell_msg;
    private Button mSubmit_btn;

   private PopupWindow mPopupwindow;
    private View mView;
    private ImageView mAlertCha_img;
    private RelativeLayout mZhiFuBao_rl,mWeiXin_rl;
    private ImageView mSelect_ZhiFuBao_img,mSelect_WeiXin_img;
    private Button mAlertSubmit_btn;

    private int mFlat=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_input_sure);

        initView();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.money2_back);
        mBack.setOnClickListener(this);
        //输入金额
        mEdit_input= (EditText) findViewById(R.id.edit_input_money);
        mEdit_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMoney_many.setText(s.toString()+"元");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //入表金额
        mMoney_many= (TextView) findViewById(R.id.input_money_many_msg);
        //立即缴费
        mSubmit_btn= (Button) findViewById(R.id.money_submit_money);
        mSubmit_btn.setOnClickListener(this);



        mView=LayoutInflater.from(this).inflate(R.layout.money_alert_box,null);
        mAlertCha_img= (ImageView) mView.findViewById(R.id.money_cha_img);
        mAlertCha_img.setOnClickListener(this);
        //支付宝
        mZhiFuBao_rl= (RelativeLayout) mView.findViewById(R.id.select_zhifubao_rl);
        mZhiFuBao_rl.setOnClickListener(this);
        mSelect_ZhiFuBao_img=(ImageView) mView.findViewById(R.id.select_zhifubao_img);
        //微信
        mWeiXin_rl= (RelativeLayout) mView.findViewById(R.id.select_weixin_rl);
        mWeiXin_rl.setOnClickListener(this);
        mSelect_WeiXin_img=(ImageView) mView.findViewById(R.id.select_weixin_img);
        //提交选择支付宝或微信
        mAlertSubmit_btn= (Button) mView.findViewById(R.id.submit_money_alert);
        mAlertSubmit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
            //确认缴费
        }else if (id==mSubmit_btn.getId()){
            if (getMoney().equals("")){
                Toast.makeText(this,"请输入缴费金额",Toast.LENGTH_SHORT).show();
            }else {
                showPopuWindow();

            }

        }else if (id==mAlertCha_img.getId()){
            mPopupwindow.dismiss();
        }else if (id==mZhiFuBao_rl.getId()){
            mFlat=1;
            mSelect_ZhiFuBao_img.setImageResource(R.mipmap.money_select);
            mSelect_WeiXin_img.setImageResource(R.mipmap.money_select_no);
        }else if (id==mWeiXin_rl.getId()){
            mFlat=2;
            mSelect_ZhiFuBao_img.setImageResource(R.mipmap.money_select_no);
            mSelect_WeiXin_img.setImageResource(R.mipmap.money_select);
            //弹框确认选择支付宝或微信缴费
        }else if (id==mAlertSubmit_btn.getId()){
            if (mFlat==1){
                Toast.makeText(this,"支付宝缴费",Toast.LENGTH_SHORT).show();
            }else if (mFlat==2){
                Toast.makeText(this,"微信缴费",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"请选择缴费方式",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getMoney(){
        if (mEdit_input.getText().toString().equals("")){
            return "";
        }
        return mEdit_input.getText().toString();
    }

    private void showPopuWindow(){
        //设置透明度
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //存放popupWindow的容器
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
        mPopupwindow=new PopupWindow(mView);
        //设置弹框的款，高
        mPopupwindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupwindow.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupwindow.setOutsideTouchable(true);//设置内容外可以点击

        // 设置背景，否则点击内容外，关闭弹窗失效
        mPopupwindow.setBackgroundDrawable(getResources().getDrawable(R.color.pop_bg));
        mPopupwindow.setAnimationStyle(R.style.popup3_anim);
        //相对于父控件的位置
        mPopupwindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

}
