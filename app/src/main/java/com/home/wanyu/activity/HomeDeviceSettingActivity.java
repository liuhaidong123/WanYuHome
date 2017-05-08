package com.home.wanyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.adapter.HomeSceneSettingListAdapter;
import com.home.wanyu.lzhUtils.MyActivity;
import com.home.wanyu.lzhUtils.MyToast;
import com.home.wanyu.lzhUtils.UtilsBitmap;
import com.home.wanyu.myview.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//设备选项中房间设置页面
public class HomeDeviceSettingActivity extends MyActivity {

    @BindView(R.id.home_device_roomsetting_rela_eidtext) EditText home_sence_scene_rela_eidtext;
    @BindView(R.id.home_device_roomsetting_listview)MyListView home_sence_listview;
    @BindView(R.id.home_device_roomsetting_rela_condition_imageView)ImageView home_device_roomsetting_rela_condition_imageView;//添加按钮的image
    private ArrayList<Map<String,String>> list;
    private String[]title={"客厅灯","电视","客厅插座"};
    private int[]url={R.mipmap.light,R.mipmap.tv,R.mipmap.socket};
    private HomeSceneSettingListAdapter adapter;

    private File outputImage;//存放选择的图片的文件
    private Boolean isBitChange=false;//是否更换了图片
    private Bitmap bit;
    private String bit64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChildView(R.layout.activity_home_device_setting);
        unbinder= ButterKnife.bind(this,ChildView);
        setTitle("房间设置");
        ShowChildView(DEFAULTRESID);
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        int size=title.length;
        for (int i=0;i<size;i++){
            Map<String,String>m=new HashMap<>();
            m.put("title",title[i]);
            m.put("url",url[i]+"");
            m.put("state","0");
            list.add(m);
        }
        adapter = new HomeSceneSettingListAdapter(list,HomeDeviceSettingActivity.this);
        home_sence_listview.setAdapter(adapter);
    }

    private void initView() {
        String name=getIntent().getStringExtra("name");
        home_sence_scene_rela_eidtext.setText(name);
    }
    @OnClick({R.id.home_device_roomsetting_rela_condition_relaLayout,R.id.activity_device_roomsetting_rela_add})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.home_device_roomsetting_rela_condition_relaLayout:
                MyToast.DebugToast(con,"添加照片");
                SearchPhoto();
                break;
            case R.id.activity_device_roomsetting_rela_add:
                MyToast.DebugToast(con,"添加设备");
                break;
        }
    }
    //初始化网络请求
    @Override
    public void getSerVerData() {

    }



    //图库选取
    private void SearchPhoto() {
        if (Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
            }
            else {
                outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"room"+".jpg");
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
                            bit64= UtilsBitmap.bitmapToBase64(bit);
                            home_device_roomsetting_rela_condition_imageView.setImageBitmap(bit);
                        }
                        else {
                            Toast.makeText(HomeDeviceSettingActivity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(HomeDeviceSettingActivity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HomeDeviceSettingActivity.this,"存储权限被禁用，无法选取图片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
