package com.home.wanyu.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.C_View.HomePictureSelectPresenter;
import com.home.wanyu.Ip.mToast;
import com.home.wanyu.PermissionCheck.FilePath;
import com.home.wanyu.PermissionCheck.IntentAction;
import com.home.wanyu.PermissionCheck.PermissionCheck;
import com.home.wanyu.PermissionCheck.PermissionFile;
import com.home.wanyu.PermissionCheck.PermissionName;
import com.home.wanyu.R;
import com.home.wanyu.mEmeu.Success;
import com.home.wanyu.myUtils.CMyActivity;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;

//房间照片选取
public class RoomPictureSelectActivity extends CMyActivity implements HomePictureSelectPresenter.IHomePicture{


    final  int REQUEST_CAMERA=1;//拍照
    final int REQUEST_PHOTO=2;//相册选取
    final int REQUEST_CURT=0;//图片裁剪

    final int PRICODE_CAMERA=0;//相机权限
    final int PRICODE_STROGE=1;//存储卡全县
    File outImage;
    HomePictureSelectPresenter presenter;
    @BindView(R.id.homepicture_select_defaultImageA)ImageView homepicture_select_defaultImageA;//默认图片1
    @BindView(R.id.homepicture_select_defaultImageB)ImageView homepicture_select_defaultImageB;//默认图片2
    @BindView(R.id.picture)ImageView picture;//显示相册中查询到的第一张图拼啊
    @BindView(R.id.pictureNumber)TextView pictureNumber;//显示相册图片数量的view
    List<Map<String,String>>list;//图库图片路径

//    int type=0;//0拍照，1相册选取
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_picture_select);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("房间照片","--",true);
        unbinder= ButterKnife.bind(this);
        presenter=new HomePictureSelectPresenter(this,this);
        presenter.getPicture();
        outImage=FilePath.getInstance().getPath(this,"room.jpg");
    }

    @OnClick({R.id.homepicture_select_defaultImageA,R.id.homepicture_select_defaultImageB,R.id.homepicture_select_layout_camera,R.id.homepicture_select_layout_photo})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.homepicture_select_defaultImageA:
                presenter.showPicture(0,null);
                break;
            case R.id.homepicture_select_defaultImageB:
                presenter.showPicture(1,null);
                break;
            case R.id.homepicture_select_layout_camera://拍照
                getRoomImageCamrea();
                break;
            case R.id.homepicture_select_layout_photo://选取图片
                getRoomImagePhoto();//图库选取照片
                break;
        }
    }

    //拍照
    public void getRoomImageCamrea(){
        if (Build.VERSION.SDK_INT>=23){
            if (PermissionCheck.checkStroagePri(this,new String[]{PermissionName.CAMERA,PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE})){
                IntentAction.getInstance().takePhoto(this,outImage,REQUEST_CAMERA);
            }
            else {
                requestPermissions(new String[]{PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE, PermissionName.CAMERA},PRICODE_STROGE);
            }
        }
        else {
            IntentAction.getInstance().takePhoto(this,outImage,REQUEST_CAMERA);
        }

    }
    //获取房间照片
    public void getRoomImagePhoto(){
        if (Build.VERSION.SDK_INT>=23){
            if (PermissionCheck.checkStroagePri(this,new String[]{PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE,PermissionName.CAMERA})){
                IntentAction.getInstance().getImage(this,outImage,REQUEST_PHOTO);
            }
            else {
                requestPermissions(new String[]{PermissionName.READ_EXTERNAL_STORAGE,PermissionName.WRITE_EXTERNAL_STORAGE,PermissionName.CAMERA},PRICODE_STROGE);
            }
        }
        else {
            IntentAction.getInstance().getImage(this,outImage,REQUEST_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PRICODE_CAMERA://相机全县
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED&&grantResults[2]==PackageManager.PERMISSION_GRANTED)
                {
                    IntentAction.getInstance().takePhoto(this,outImage,REQUEST_CAMERA);
                }
                else {
                    mToast.Toast(this, PermissionFile.CAMERA);
                }
                break;
            case PRICODE_STROGE://存储卡全县
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    IntentAction.getInstance().getImage(this,outImage,REQUEST_PHOTO);
                }
                else {
                    mToast.Toast(this, PermissionFile.READ_WRITE_STROAGE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CAMERA://拍摄照片
                    IntentAction.getInstance().curtImageTakePhoto(data,700,1200,outImage,this,REQUEST_CURT);
                    break;
                case REQUEST_PHOTO://图库选择
                    IntentAction.getInstance().curtImage(data,700,1200,outImage,this,REQUEST_CURT);
                    break;
                case REQUEST_CURT://图片裁剪
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outImage.getAbsolutePath());
                        if (bitmap!=null){
                            presenter.showPicture(2,outImage);//
                        }
                        else {
                            Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void getPictureAndNumber(String firstPicturePath, int tottalPicture,Success success) {
        if (success==Success.SUCCESS){
            pictureNumber.setText(tottalPicture+"");
            Log.e("url----",firstPicturePath);
            Picasso.with(this).load(Uri.parse(firstPicturePath)).error(R.mipmap.errorphoto).into(picture);
        }
    }

}
