package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.Ip.Ip;
import com.home.wanyu.Ip.ToastType;
import com.home.wanyu.Ip.mGson;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.Ip.okhttpTools;
import com.home.wanyu.OkhttpUtils.okhttp;
import com.home.wanyu.R;
import com.home.wanyu.User.UserInfo;
import com.home.wanyu.bean.Bean_UserInfo;
import com.home.wanyu.bean.Bean_changeUserInfo;
import com.home.wanyu.bean.Bean_getRoomData;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.myview.RoundImageView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//bundle.putSerializable("data",personal);
//        intent.putExtra("data",bundle);
//用户信息修改
public class MyUserMsgEditorActivity extends MyActivity {
    @BindView(R.id.activity_my_user_msg_editor_image)RoundImageView activity_my_user_msg_editor_image;//头像
    @BindView(R.id.activity_my_user_msg_editor_Name)EditText activity_my_user_msg_editor_Name;//用户名输入框
    @BindView(R.id.activity_my_user_msg_textSex_man)TextView activity_my_user_msg_textSex_man;
    @BindView(R.id.activity_my_user_msg_textSex_wenman)TextView activity_my_user_msg_textSex_wenman;
    Bean_UserInfo.PersonalBean personal;//传递过来的用户信息
    private File outputImage;//存放选择的图片的文件
    private Boolean isBitChange=false;//是否更换了图片
    private Bitmap bit;
    private String resStr;
    private Handler han=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mToast.ToastFaild(con, ToastType.FAILD);
                    break;
                case 1:
                    try {
                        Bean_changeUserInfo changeUserInfo= mGson.gson.fromJson(resStr,Bean_changeUserInfo.class);
                        if (changeUserInfo!=null){
                            if ("0".equals(changeUserInfo.getCode())){
                                mToast.Toast(con,"修改成功");
                                Intent intent=new Intent();
                                intent.putExtra("fresh","0");
                                setResult(200,intent);
                                finish();
                            }
                            else {
                                mToast.Toast(con,changeUserInfo.getResult());
                            }
                        }
                        else {
                            mToast.ToastFaild(con,ToastType.GSONEMPTY);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        mToast.ToastFaild(con,ToastType.GSONFAILD);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleView(R.layout.activity_my_user_title);
        initChildView(R.layout.activity_my_user_msg_editor);
        unbinder= ButterKnife.bind(this,ChildView);
        ShowChildView(DEFAULTRESID);
        activity_my_user_msg_textSex_wenman.setSelected(true);
        personal= (Bean_UserInfo.PersonalBean) getIntent().getBundleExtra("data").getSerializable("data");//personal
        initData();
    }

    private void initData() {
        if (personal!=null){
            Picasso.with(con).load(Ip.imagePath+personal.getAvatar()).error(R.mipmap.loadinge).placeholder(R.mipmap.loadinge).into(activity_my_user_msg_editor_image);
            activity_my_user_msg_editor_Name.setText(personal.getUserName()==null?"未填写":personal.getUserName());
            int gender=personal.getGender();   //|性别1=男，2=女
            if (gender==1){
                activity_my_user_msg_textSex_man.setSelected(true);
                activity_my_user_msg_textSex_wenman.setSelected(false);
            }
        }
        else {
            personal=new Bean_UserInfo.PersonalBean();
        }
    }

    @OnClick({R.id.activity_my_user_msg_editor_ChangeImage,R.id.activity_my_user_msg_textSex_man,R.id.activity_my_user_msg_textSex_wenman})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.activity_my_user_msg_editor_ChangeImage://更改iamge的view
                SearchPhoto();
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
        String name=activity_my_user_msg_editor_Name.getText().toString();
        if ("".equals(name)| TextUtils.isEmpty(name)){
            mToast.Toast(con,"用户名不正确");
            return;
        }
        HashMap<String,String>map=new HashMap<>();
        map.put("token", UserInfo.userToken);
        map.put("userName",name);
        List<File> list=new ArrayList<>();
        if (isBitChange){
            list.add(outputImage);
        }
        if (activity_my_user_msg_textSex_wenman.isSelected()){
            personal.setGender(2);
            map.put("gender","2");
        }
        else {
            personal.setGender(1);
            map.put("gender","1");
        }
        okhttp.getOkhttpFileCallCooki(map,list,Ip.serverPath+Ip.interface_changeUserInfo,UserInfo.userToken).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                han.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("个人信息修改／保存--",resStr);
                han.sendEmptyMessage(1);
            }
        });
    }
    //图库选取
    private void SearchPhoto() {
        if (Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
            }
            else {
                outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"userInfo"+".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            }
        }
        else {
            outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"room"+".jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 20:
                    //此处启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //此处注释掉的部分是针对android 4.4路径修改的一个测试
                    //有兴趣的读者可以自己调试看看
                    intent.setDataAndType(data.getData(), "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 300);//宽度
                    intent.putExtra("outputY", 300);//高度
//                    intent.putExtra("return-data", true);
//                    intent.putExtra("noFaceDetection", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                    startActivityForResult(intent, 12);
                    break;
                case 12:
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
                        if (bitmap!=null){
                            isBitChange=true;
                            bit=bitmap;
                            activity_my_user_msg_editor_image.setImageBitmap(bit);
                        }
                        else {
                            Toast.makeText(con,"照片截取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(con,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 11:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SearchPhoto();
                }
                else {
                    Toast.makeText(con,"存储权限被禁用，无法选取图片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
