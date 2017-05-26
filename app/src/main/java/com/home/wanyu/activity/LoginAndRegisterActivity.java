package com.home.wanyu.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.fragment.LoginFragment;
import com.home.wanyu.fragment.registerFragment;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhView.MyRelayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//登陆注册的activity
public class LoginAndRegisterActivity extends FragmentActivity {
    Unbinder unbinder;
    @BindView(R.id.login_register_text_login)TextView login_register_text_login;//登录的view
    @BindView(R.id.login_register_text_register)TextView login_register_text_register;//注册的view
    @BindView(R.id.login_register_Mylayout)MyRelayout login_register_Mylayout;
    @BindView(R.id.login_register_fragmentLayout)RelativeLayout login_register_fragmentLayout;//fragment切换的view
    private LoginFragment loginFragment;//登录的fragment
    private registerFragment registerFragment;//注册的fragment
    private Fragment mFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        unbinder= ButterKnife.bind(this);
        login_register_Mylayout.setColor(getResources().getColor(R.color.login_start),getResources().getColor(R.color.login_end));
        loginFragment=new LoginFragment();
        registerFragment=new registerFragment();
        mFrag=loginFragment;
        login_register_text_login.setSelected(true);
        getSupportFragmentManager().beginTransaction().add(R.id.login_register_fragmentLayout,loginFragment).add(R.id.login_register_fragmentLayout,registerFragment)
                .hide(loginFragment).hide(registerFragment).show(mFrag).commit();
    }

    @OnClick({R.id.login_register_text_login,R.id.login_register_text_register})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.login_register_text_login://登录
                getSupportFragmentManager().beginTransaction().hide(mFrag).commit();
                login_register_text_login.setSelected(true);
                login_register_text_register.setSelected(false);
                mFrag=loginFragment;
                getSupportFragmentManager().beginTransaction().show(mFrag).commit();
                break;
            case R.id.login_register_text_register://注册
                getSupportFragmentManager().beginTransaction().hide(mFrag).commit();
                login_register_text_login.setSelected(false);
                login_register_text_register.setSelected(true);
                mFrag=registerFragment;
                getSupportFragmentManager().beginTransaction().show(mFrag).commit();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }


}
