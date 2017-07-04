package com.home.wanyu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.getAreaPostImg.Root;

import net.tsz.afinal.http.AjaxParams;

public class CarPoolingCommentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mback;
    private EditText editText;
    private TextView mPost_btn;
    private String flag;

    private HttpTools httptools;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 122) {//社区活动评论
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CarPoolingCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 129) {//社区拼车评论
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityComment.Root) {
                    com.home.wanyu.bean.getAreaActivityComment.Root root = (com.home.wanyu.bean.getAreaActivityComment.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CarPoolingCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CarPoolingCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pooling_comment);
        httptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {


        mback = (ImageView) findViewById(R.id.car_msg_back);
        mback.setOnClickListener(this);

        mPost_btn = (TextView) findViewById(R.id.post_all);
        mPost_btn.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.car_comment_msg);
        editText.setHint(getIntent().getStringExtra("hint"));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        } else if (id == mPost_btn.getId()) {
            if (!getContent().equals("")) {
                mPost_btn.setClickable(false);
                if (getIntent().getStringExtra("flag").equals("activity")) {//社区活动传过来的评论
                    AjaxParams ajax=new AjaxParams();
                    ajax.put("token",UserInfo.userToken);
                    ajax.put("ActivityId",getIntent().getLongExtra("Id", -1)+"");
                    ajax.put("coverPersonalId", getIntent().getLongExtra("coverPersonalId", -1)+"");
                    ajax.put("content",getContent());

                    Log.e("coverPersonalId",getIntent().getLongExtra("coverPersonalId", -1)+"");
                    httptools.AreaActivityComment(mhandler,ajax);

                } else if (getIntent().getStringExtra("flag").equals("carPooling")){//社区拼车评论

                    AjaxParams ajax=new AjaxParams();
                    ajax.put("token",UserInfo.userToken);
                    ajax.put("carpoolingId",getIntent().getLongExtra("Id", -1)+"");
                    ajax.put("coverPersonalId", getIntent().getLongExtra("coverPersonalId", -1)+"");
                    ajax.put("content",getContent());

                    Log.e("carpoolingId",getIntent().getLongExtra("Id", -1)+"");
                    Log.e("coverPersonalId",getIntent().getLongExtra("coverPersonalId", -1)+"");
                    httptools.carPoolingComment(mhandler,ajax);
                }

            } else {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getContent() {
        if (editText.getText().toString().trim().equals("")) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}
