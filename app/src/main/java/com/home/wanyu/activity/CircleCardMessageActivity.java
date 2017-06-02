package com.home.wanyu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.CIrcleExpandableAda;
import com.home.wanyu.apater.CircleCommendLikeAda;
import com.home.wanyu.apater.CircleCommentAda;
import com.home.wanyu.apater.RecordListviewGridviewAda;
import com.home.wanyu.bean.getCircleCardList.Result;
import com.home.wanyu.bean.getCircleCommentMsg.Comment;
import com.home.wanyu.bean.getCircleCommentMsg.LikeNum;
import com.home.wanyu.bean.getCircleLike.Root;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyExpandableListview;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CircleCardMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mDelete;
    private MyListView mListView;
    private CircleCommentAda mAdapter;
    private List<Comment> mCommentList = new ArrayList<>();

    private MyGridView mGridview, mImgGridView;
    private CircleCommendLikeAda mGridviewAda;
    private RecordListviewGridviewAda mImgAdapter;
    private List<LikeNum> mLikeList = new ArrayList<>();


    private RoundImageView mHead;
    private TextView mName, mArea, mType, mMsg, mTime, mLikeNum, mCommentNum;
    private ImageView mLike_img;

    private RelativeLayout mScroll_rl;
    private EditText mEdit_content;
    private int start = 0;
    private int limmit = 10;
    private long stateId = -1;
    private long userid = -1;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 113) {//点赞接口
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CircleCardMessageActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                        Picasso.with(CircleCardMessageActivity.this).load(R.mipmap.circle_like).error(R.mipmap.error_small).into(mLike_img);
                        mLikeNum.setText(((Integer.valueOf(mLikeNum.getText().toString()) + 1) + ""));
                        mHttptools.getCircleCommentList(mHandler, UserInfo.userToken, stateId);//获取评论列表接口
                    } else {
                        Toast.makeText(CircleCardMessageActivity.this, "撤销点赞", Toast.LENGTH_SHORT).show();
                        Picasso.with(CircleCardMessageActivity.this).load(R.mipmap.circle_like_no).error(R.mipmap.error_small).into(mLike_img);
                        mLikeNum.setText(((Integer.valueOf(mLikeNum.getText().toString()) - 1) + ""));
                        mHttptools.getCircleCommentList(mHandler, UserInfo.userToken, stateId);//获取评论列表接口
                        }
                }
            } else if (msg.what == 115) {//获取评论列表
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getCircleCommentMsg.Root) {
                    com.home.wanyu.bean.getCircleCommentMsg.Root root = (com.home.wanyu.bean.getCircleCommentMsg.Root) o;
                    if (root.getCode().equals("0")) {

                        Picasso.with(CircleCardMessageActivity.this).load(UrlTools.BASE + root.getResult().getStateEntity().getAvatar()).resize(ImgUitls.getWith(CircleCardMessageActivity.this) / 3, ImgUitls.getWith(CircleCardMessageActivity.this) / 3).error(R.mipmap.error_small).into(mHead);
                        mName.setText(root.getResult().getStateEntity().getUserName());
                        mArea.setText(root.getResult().getStateEntity().getRname());
                        mType.setText(root.getResult().getStateEntity().getCname());
                        mMsg.setText(root.getResult().getStateEntity().getContent());
                        mTime.setText(root.getResult().getStateEntity().getCreateTimeString());
                        mLikeNum.setText(root.getResult().getStateEntity().getLikeNum() + "");
                        mCommentNum.setText(root.getResult().getStateEntity().getCommentNum() + "");

                        if (root.getResult().getStateEntity().islike()) {
                            Picasso.with(CircleCardMessageActivity.this).load(R.mipmap.circle_like).error(R.mipmap.error_small).into(mLike_img);
                        } else {
                            Picasso.with(CircleCardMessageActivity.this).load(R.mipmap.circle_like_no).error(R.mipmap.error_small).into(mLike_img);
                        }

                        if (UserInfo.personalId == root.getResult().getStateEntity().getPersonalId()) {
                            mDelete.setVisibility(View.VISIBLE);
                        } else {
                            mDelete.setVisibility(View.GONE);
                        }
                        if (!root.getResult().getStateEntity().getPicture().equals("")) {
                            String[] imgstr = root.getResult().getStateEntity().getPicture().split(";");
                            mImgGridView.setAdapter(new RecordListviewGridviewAda(CircleCardMessageActivity.this, imgstr));
                        } else {
                            String[] imgstr = new String[0];
                            mImgGridView.setAdapter(new RecordListviewGridviewAda(CircleCardMessageActivity.this, imgstr));
                        }


                        mCommentList = root.getResult().getComment();
                        mLikeList = root.getResult().getLikeNum();
                        mAdapter.setmCommentList(mCommentList);
                        mAdapter.notifyDataSetChanged();
                        mGridviewAda.setList(mLikeList);
                        mGridviewAda.notifyDataSetChanged();

                    } else {
                        Toast.makeText(CircleCardMessageActivity.this, "无法获取评论列表", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (msg.what == 117) {//删除
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getCircleDeleteResult.Root) {
                    com.home.wanyu.bean.getCircleDeleteResult.Root root = (com.home.wanyu.bean.getCircleDeleteResult.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CircleCardMessageActivity.this, root.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CircleCardMessageActivity.this, root.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_card_message);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        stateId = getIntent().getLongExtra("stateid", -1);
        Log.e("最后stateId=", stateId + "");
        if (stateId != -1) {
            mHttptools.getCircleCommentList(mHandler, UserInfo.userToken, stateId);//获取评论列表接口
        }
        mBack = (ImageView) findViewById(R.id.circle_card_msg_back);
        mBack.setOnClickListener(this);

        mDelete = (TextView) findViewById(R.id.circle_card_msg_delete);
        mDelete.setOnClickListener(this);

        //发送按钮
        mEdit_content = (EditText) findViewById(R.id.edit);


        mHead = (RoundImageView) findViewById(R.id.circle_card_head_img);
        mName = (TextView) findViewById(R.id.circle_card_name_tv);
        mArea = (TextView) findViewById(R.id.circle_card_area_tv);
        mType = (TextView) findViewById(R.id.circle_card_type_tv);
        mMsg = (TextView) findViewById(R.id.circle_card_commend_msg);
        mTime = (TextView) findViewById(R.id.circle_card_time_tv);
        mLikeNum = (TextView) findViewById(R.id.circle_like_num);
        mCommentNum = (TextView) findViewById(R.id.circle_card_commend_num);
        mLike_img = (ImageView) findViewById(R.id.circle_like_img);
        //点赞
        mLike_img.setOnClickListener(this);


        //【评论列表
        mListView = (MyListView) findViewById(R.id.comment_listview_circle);
        mAdapter = new CircleCommentAda(this, mCommentList, mEdit_content, mCommentNum, stateId);
        mListView.setAdapter(mAdapter);

        //点赞头像GridView
        mGridview = (MyGridView) findViewById(R.id.circle_card_gridview);
        mGridviewAda = new CircleCommendLikeAda(this, mLikeList);
        mGridview.setAdapter(mGridviewAda);
        //点击点赞头像跳转到点赞人的个人信息页面
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CircleCardMessageActivity.this, OtherPersonInfoActivity.class);
                intent.putExtra("id", mLikeList.get(position).getPersonalId() + "");
                startActivity(intent);
            }
        });

        mImgGridView = (MyGridView) findViewById(R.id.circle_gridview_friend);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mLike_img.getId()) {//点赞
            mHttptools.getCIrcleLikeResult(mHandler, UserInfo.userToken, stateId);
        } else if (id == mDelete.getId()) {//删除
            mHttptools.getCircleDeleteResult(mHandler, UserInfo.userToken, stateId);
        }
    }


}
