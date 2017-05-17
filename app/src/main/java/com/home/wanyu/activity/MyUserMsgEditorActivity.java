package com.home.wanyu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.home.wanyu.Ip.mToast;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//用户信息修改
public class MyUserMsgEditorActivity extends MyActivity {
    @BindView(R.id.activity_my_user_msg_editor_image)RoundImageView activity_my_user_msg_editor_image;//头像
    @BindView(R.id.activity_my_user_msg_editor_Name)EditText activity_my_user_msg_editor_Name;//用户名输入框
    @BindView(R.id.activity_my_user_msg_textSex_man)TextView activity_my_user_msg_textSex_man;
    @BindView(R.id.activity_my_user_msg_textSex_wenman)TextView activity_my_user_msg_textSex_wenman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.activity_my_user_title);
        initChildView(R.layout.activity_my_user_msg_editor);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        activity_my_user_msg_textSex_wenman.setSelected(true);

    }
    @OnClick({R.id.activity_my_user_msg_editor_ChangeImage,R.id.activity_my_user_msg_textSex_man,R.id.activity_my_user_msg_textSex_wenman})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_user_msg_editor_ChangeImage://更改iamge的view
                mToast.Toast(con,"更换头像");
                break;
            case R.id.activity_my_user_msg_textSex_man:
                activity_my_user_msg_textSex_wenman.setSelected(false);
                activity_my_user_msg_textSex_man.setSelected(true);
                break;
            case R.id.activity_my_user_msg_textSex_wenman:
                activity_my_user_msg_textSex_wenman.setSelected(true);
                activity_my_user_msg_textSex_man.setSelected(false);
                break;
        }
    }
    @Override
    public void getSerVerData() {

    }
    public void Submit(View vi){
        mToast.Toast(con,"提交修改");
    }
}
