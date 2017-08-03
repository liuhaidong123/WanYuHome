package com.home.wanyu.C_View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.home.wanyu.PopUtils.PopupSettings;
import com.home.wanyu.R;
import com.home.wanyu.lzhUtils.BitmapUtils;
import com.home.wanyu.mEmeu.Success;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by wanyu on 2017/7/24.
 */

public class HomePictureSelectPresenter implements View.OnClickListener{
    final int REQUESTCODE=100;//上文的请求吗
    Activity activity;
    IHomePicture iHomePicture;
    List<Map<String,String>> list;
    PopupWindow pop;
    File outputImage;
    int tp;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
          switch (msg.what){
              case -1://查询图库
                  if (list!=null&&list.size()>0){
                      Random random=new Random();
                      int ids=random.nextInt(list.size()-1);
                      iHomePicture.getPictureAndNumber(list.get(ids).get("url"),list.size(),Success.SUCCESS);
                  }
                  else{
                      iHomePicture.getPictureAndNumber("",0,Success.FAILED);
                  }
                  break;
          }
        }
    };
    //构造器
    public HomePictureSelectPresenter(Activity activity,IHomePicture iHomePicture){
        this.activity=activity;
        this.iHomePicture=iHomePicture;
    }

    //查询图片
    public void getPicture(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                list=BitmapUtils.getCursor(activity);
                handler.sendEmptyMessage(-1);
            }
        }).start();
    }

    //弹窗展示图片type 0默认图片1，1代表默认图片2，2代表相机或者拍照
    public void showPicture(int type, File f){
        tp=type;
        if (pop==null){
            pop=new PopupWindow();
        }
        View vi= LayoutInflater.from(activity).inflate(R.layout.pop_roompicture,null);
        TextView pop_roomimg_textVSubmit= (TextView) vi.findViewById(R.id.pop_roomimg_textVSubmit);
        pop_roomimg_textVSubmit.setOnClickListener(this);
        TextView pop_roomimg_textVCancle= (TextView) vi.findViewById(R.id.pop_roomimg_textVCancle);
        pop_roomimg_textVCancle.setOnClickListener(this);
        ImageView pop_roomimg_imageview= (ImageView) vi.findViewById(R.id.pop_roomimg_imageview);
        switch (type){
            case 0://图片1
                pop_roomimg_imageview.setImageResource(R.mipmap.pictureone);
                break;
            case 1://图片2
                pop_roomimg_imageview.setImageResource(R.mipmap.picturetwo);
                break;
            case 2://相册或者拍照
                outputImage=f;
                Bitmap bt= BitmapFactory.decodeFile(f.getAbsolutePath());
                pop_roomimg_imageview.setImageBitmap(bt);
                break;
        }
        View parent=activity.findViewById(R.id.activity_home_picture_select);
        PopupSettings.getInstance().windowActivityCenter2(pop,parent,activity,vi);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_roomimg_textVSubmit://设置
                pop.dismiss();
                Intent intent=new Intent();
                switch (tp){
                    case 0://图片1
                        intent.putExtra("type",0);
                        break;
                    case 1://图片2
                        intent.putExtra("type",1);
                        break;
                    case 2://拍照/相册选取
                // type:0代表选用默认图片1，1代表选用默认图片2，2代表相机或者手机图库中的图片
                        intent.putExtra("type",2);
                        intent.putExtra("image",outputImage.getAbsolutePath());
                        break;
                }
                activity.setResult(REQUESTCODE,intent);
                activity.finish();
                break;
            case R.id.pop_roomimg_textVCancle://取消
                pop.dismiss();
                break;
        }

    }


    public interface IHomePicture{
        void getPictureAndNumber(String firstPicturePath,int pictureNumber,Success success);//首张图片的地址，图片的个数
    }
}
