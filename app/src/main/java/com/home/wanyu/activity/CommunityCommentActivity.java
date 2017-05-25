package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.HttpUtils.UrlTools;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.apater.AreaActivityCommentAda;
import com.home.wanyu.apater.AreaActivityImgAda;
import com.home.wanyu.apater.AreaActivityJoinAda;
import com.home.wanyu.apater.AreaActivityLikeAda;
import com.home.wanyu.apater.HousePostImgAda;
import com.home.wanyu.bean.getAreaActivityList.Result;
import com.home.wanyu.bean.getAreaActivityMsg.ActivityLoglist;
import com.home.wanyu.bean.getAreaActivityMsg.Activitypicturelist;
import com.home.wanyu.bean.getAreaActivityMsg.Commentlist;
import com.home.wanyu.bean.getAreaActivityMsg.Root;
import com.home.wanyu.bean.getAreaActivityMsg.UpVptelist;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myview.MyGridView;
import com.home.wanyu.myview.MyListView;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommunityCommentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private LinearLayout mComment_ll, mLike_ll, mJoin_ll;
    private ImageView mLike_img, mJoin_img;

    private Result result;
    private int over;
    private long userId;
    private RoundImageView mHead_img;
    private TextView mName, mtime, mState, mTitle, mTime_msg, mAddress, mPerson_num, mPhone, mContent;
    private TextView mDelete_btn;//删除按钮

    private MyGridView mLikeGridView, mJoinGridView, mImgGridView;
    private MyListView mCommentListView;
    private AreaActivityLikeAda mLikeAda;
    private AreaActivityJoinAda mJoinAda;
    private AreaActivityImgAda mImgAda;
    private List<UpVptelist> mLikeList = new ArrayList<>();
    private List<ActivityLoglist> mJoinList = new ArrayList<>();
    private List<Activitypicturelist> mImgList = new ArrayList<>();
    private List<String> mImgStrList = new ArrayList<>();

    private List<Commentlist> mCommentList = new ArrayList<>();
    private AreaActivityCommentAda mCommentAda;
    private int imgNum = 2;//判断用户上传过几张图片
    private int mSize;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 119) {//活动详情
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getIslike()) {
                        mLike_img.setImageResource(R.mipmap.circle_like);
                    } else {
                        mLike_img.setImageResource(R.mipmap.circle_like_no);
                    }

                    if (root.getJoined()) {
                        mJoin_img.setImageResource(R.mipmap.community_add);
                    } else {
                        mJoin_img.setImageResource(R.mipmap.community_add_no);
                    }

                    mLikeList = root.getResult().getUpVptelist();
                    mJoinList = root.getResult().getActivityLoglist();
                    mImgList = root.getResult().getActivitypicturelist();
                    mCommentList = root.getResult().getCommentlist();
                    mLikeAda = new AreaActivityLikeAda(CommunityCommentActivity.this, mLikeList);
                    mLikeGridView.setAdapter(mLikeAda);
                    mJoinAda = new AreaActivityJoinAda(CommunityCommentActivity.this, mJoinList);
                    mJoinGridView.setAdapter(mJoinAda);


                    mCommentAda = new AreaActivityCommentAda(CommunityCommentActivity.this, mCommentList, result.getId());
                    mCommentListView.setAdapter(mCommentAda);

                    mImgStrList.clear();
                    //取出图片
                    for (int i = 0; i < mImgList.size(); i++) {
                        //判断自己曾经上传过几张图片
                        if (mImgList.get(i).getPersonalId() == userId) {
                            if (mImgList.get(i).getPicture().equals("")) {
                                imgNum = 2;//可以传2张
                            } else {
                                if (mImgList.get(i).getPicture().split(";").length == 1) {
                                    imgNum = 1; //可以传1张
                                } else if (mImgList.get(i).getPicture().split(";").length == 2) {
                                    imgNum = 0; //不可以传图片
                                }
                            }
                        }
                        //获取所有用户上传过的图片
                        if (!mImgList.get(i).getPicture().equals("")) {
                            String[] str = mImgList.get(i).getPicture().split(";");
                            for (int j = 0; j < str.length; j++) {
                                mImgStrList.add(str[j]);
                            }
                        }

                        mSize = mImgStrList.size();
                    }
                    mImgAda = new AreaActivityImgAda(CommunityCommentActivity.this, mImgStrList, mSize);
                    mImgGridView.setAdapter(mImgAda);
                }
            } else if (msg.what == 121) {//发布图片
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaPostImg.Root) {
                    com.home.wanyu.bean.getAreaPostImg.Root root = (com.home.wanyu.bean.getAreaPostImg.Root) o;

                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "上传活动图片成功", Toast.LENGTH_SHORT).show();
                        if (result != null) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, result.getId());
                        }
                    } else {
                        Toast.makeText(CommunityCommentActivity.this, "上传活动图片失败", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 120) {//点赞
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                        if (result != null) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, result.getId());
                        }
                        mLike_img.setImageResource(R.mipmap.circle_like);
                    }else {
                        Toast.makeText(CommunityCommentActivity.this, "取消点赞", Toast.LENGTH_SHORT).show();
                        mLike_img.setImageResource(R.mipmap.circle_like_no);
                    }
                }

            }else if (msg.what == 123) {//加入
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(CommunityCommentActivity.this, "参加活动成功", Toast.LENGTH_SHORT).show();
                        if (result != null) {
                            //获取活动详情
                            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, result.getId());
                        }
                        mJoin_img.setImageResource(R.mipmap.community_add);
                    }else {
                        Toast.makeText(CommunityCommentActivity.this, "您已参加活动", Toast.LENGTH_SHORT).show();

                    }
                }

            }else if (msg.what==125){//删除
                Object o = msg.obj;
                if (o != null && o instanceof com.home.wanyu.bean.getAreaActivityLike.Root) {
                    com.home.wanyu.bean.getAreaActivityLike.Root root = (com.home.wanyu.bean.getAreaActivityLike.Root) o;
                    if (root.getCode().equals("0")){
                        Toast.makeText(CommunityCommentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(CommunityCommentActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_comment);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        result = (Result) getIntent().getSerializableExtra("bean");
        over = getIntent().getIntExtra("over", -1);
        userId = getIntent().getLongExtra("userID", -1);
        mDelete_btn = (TextView) findViewById(R.id.u_delete_btn);

        mHead_img = (RoundImageView) findViewById(R.id.u_head_img);
        mName = (TextView) findViewById(R.id.u_name);
        mtime = (TextView) findViewById(R.id.u_post_time);
        mState = (TextView) findViewById(R.id.u_state);
        mTitle = (TextView) findViewById(R.id.u_title_tv);
        mTime_msg = (TextView) findViewById(R.id.time_msg_v);
        mAddress = (TextView) findViewById(R.id.address_msg_v);
        mPerson_num = (TextView) findViewById(R.id.person_msg_v);
        mPhone = (TextView) findViewById(R.id.phone_msg_v);
        mContent = (TextView) findViewById(R.id.content_msg_tv);

        if (result != null) {
            Picasso.with(this).load(UrlTools.BASE + result.getAvatar()).resize(ImgUitls.getWith(this) / 3, ImgUitls.getWith(this) / 3).error(R.mipmap.error_small).into(mHead_img);
            mName.setText(result.getUser_name());
            mtime.setText(result.getCreateTimeString());
            if (over == 1) {
                mState.setText("正在进行");
            } else if (over == 2) {
                mState.setText("活动结束");
            }
            mTitle.setText(result.getActivityTheme());
            mTime_msg.setText(result.getStarttimeString() + "至" + result.getEndtimeString());
            mAddress.setText(result.getActivityAddress());
            mPerson_num.setText(result.getActivityNumber() + "");
            mPhone.setText(result.getActivityTelephone() + "");
            mContent.setText(result.getActivityContent());

            if (userId == result.getPersonalId()) {

                mDelete_btn.setOnClickListener(this);
                mDelete_btn.setVisibility(View.VISIBLE);
            } else {
                mDelete_btn.setVisibility(View.GONE);
            }

        }
        //点赞GridView
        mLikeGridView = (MyGridView) findViewById(R.id.community_like_gridview);
        //加入GridView
        mJoinGridView = (MyGridView) findViewById(R.id.community_join_gridview);
        //添加图片GridView
        mImgGridView = (MyGridView) findViewById(R.id.activity_img_gridview);

        //评论listview
        mCommentListView = (MyListView) findViewById(R.id.community_commend_listview);

        //选择上传图片
        mImgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (mImgStrList.size() == 0) {
                    //跳转相册或拍照
                    init(imgNum);
                } else {
                    if (mImgStrList.size() == position) {
                        if (mImgStrList.size() == 20) {
                            Toast.makeText(CommunityCommentActivity.this, "亲，最多20张图片哦", Toast.LENGTH_SHORT).show();
                        } else {
                            if (imgNum == 0) {
                                Toast.makeText(CommunityCommentActivity.this, "亲，您已经添加过2张图片了，不能再次添加了哦", Toast.LENGTH_SHORT).show();
                            } else {
                                if (mImgStrList.size() == 19) {
                                    if (imgNum == 1) {
                                        init(1);
                                    } else if (imgNum == 2) {
                                        init(1);
                                    }
                                } else {
                                    if (imgNum == 1) {
                                        init(1);
                                    } else if (imgNum == 2) {
                                        init(2);
                                    }
                                }

                            }

                        }
                    }


                }
            }
        });

        mBack = (ImageView) findViewById(R.id.community_msg_back);
        mBack.setOnClickListener(this);

        mComment_ll = (LinearLayout) findViewById(R.id.comment_ll);
        mComment_ll.setOnClickListener(this);
        mLike_ll = (LinearLayout) findViewById(R.id.like_ll);
        mLike_ll.setOnClickListener(this);
        mJoin_ll = (LinearLayout) findViewById(R.id.join_ll);
        mJoin_ll.setOnClickListener(this);
        mLike_img = (ImageView) findViewById(R.id.like_img);
        mJoin_img = (ImageView) findViewById(R.id.join_img);
    }

    private final int REQUEST_CODE_ASK_READ_PHONE = 123;

    //权限判断
    public void init(int num) {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            //如果没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                ActivityCompat.requestPermissions(this,
                        //在这个数组中可以添加很多权限
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                startSelectImgActivity(num);
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity(num);
        }
    }

    //跳转到选择图片的页面
    public void startSelectImgActivity(int i) {
        Intent intent = new Intent(this, SelectImgActivity.class);
        intent.putExtra("num", i);
        startActivityForResult(intent, 100);
    }

    //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startSelectImgActivity(imgNum);
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //选择图片以后会回掉这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");

            if (imgNum - list.size() == 0) {
                imgNum = 0;
            } else if (imgNum - list.size() == 1) {
                imgNum = 1;
            } else if (imgNum - list.size() == 2) {
                imgNum = 2;
            }

            for (int i = 0; i < list.size(); i++) {
                mImgStrList.add(list.get(i));
            }
//            mImgAda = new AreaActivityImgAda(this, mImgStrList, mSize);
//            mImgGridView.setAdapter(mImgAda);
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("token", UserInfo.userToken);
            ajaxParams.put("activity_id", result.getId() + "");

            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    try {
                        ajaxParams.put("图片" + i, transImage(list.get(i), ImgUitls.getWith(this), ImgUitls.getHeight(this), 90, "图片" + i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                ajaxParams.put("number",list.size()+"");
                mHttptools.AreaActivityPosImg(mHandler, ajaxParams);
            }
        }

    }

    /**
     * @param pathName 图片路径
     * @param width    屏幕宽度
     * @param height   屏幕 高度
     * @param quality  图片质量
     * @param fileName 图片名称
     * @return
     */
    public File transImage(String pathName, int width, int height, int quality, String fileName) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth - 0.1f;
            float scaleHeight = (float) height / bitmapHeight - 0.1f;
            Log.e("bitmapWidth", bitmapWidth + "");
            Log.e("bitmapHeight", bitmapHeight + "");
            Log.e("scaleWidth", scaleWidth + "");
            Log.e("scaleHeight", scaleHeight + "");
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            File file = null;
            //存储媒体已经挂载，并且挂载点可读/写
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//可写
                //保存
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：", Environment.DIRECTORY_PICTURES);

            } else {
                file = new File(getFilesDir(), fileName + ".jpg");
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
                bos.flush();
                bos.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }
            Log.e("--file-大小----", file.length() / 1024 + "");
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private long coverPersonalId = 0;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mComment_ll.getId()) {
            Intent intent = new Intent(this, CarPoolingCommentActivity.class);
            intent.putExtra("flag", "activity");
            intent.putExtra("coverPersonalId", coverPersonalId);
            intent.putExtra("Id", result.getId());
            intent.putExtra("hint", "说点什么");
            startActivity(intent);
        } else if (id == mLike_ll.getId()) {//点赞
            if (result != null) {
                mHttptools.getAreaActivityLike(mHandler, UserInfo.userToken, result.getId());
            }

        }else if (id==mJoin_ll.getId()){//参加
            if (result != null) {
                mHttptools.areaActivityJoin(mHandler, UserInfo.userToken, result.getId());
            }
        }else if (id==mDelete_btn.getId()){//删除
            if (result!=null){
                mHttptools.areaActivityDelete(mHandler,UserInfo.userToken,result.getId(),result.getPersonalId());
                Log.e("result.getId()",result.getId()+"");
                Log.e("result.getPersonalId()",result.getPersonalId()+"");

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (result != null) {
            //获取活动详情
            mHttptools.getAreaActivityMsg(mHandler, UserInfo.userToken, result.getId());
        }

    }
}
