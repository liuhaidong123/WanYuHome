package com.home.wanyu.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.SelectImgAda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectImgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mText;
    private GridView mGridView;
    private SelectImgAda mAdapter;
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayList<String> mImgList = new ArrayList<>();
    private Cursor cursor;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.select_img_back);
        mBack.setOnClickListener(this);
        //完成
        mText = (TextView) findViewById(R.id.title_tv);
        mText.setOnClickListener(this);
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        selectImg();

        mGridView = (GridView) findViewById(R.id.select_img_gridview);
        mAdapter = new SelectImgAda(this, mList);
        mGridView.setAdapter(mAdapter);
        //点击选择相片
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.select_small_img);
                Log.e("文件", mAdapter.getItem(position).toString());
                if (num != getIntent().getIntExtra("num",-1)) {
                    if (mImgList.contains( mAdapter.getItem(position))) {
                        mImgList.remove( mAdapter.getItem(position));
                        num--;
                        mText.setText("完成" + num + "/" + getIntent().getIntExtra("num",-1));
                        imageView.setImageResource(R.mipmap.selectfa);
                    } else {
                        num++;
                        mText.setText("完成" + num + "/" + getIntent().getIntExtra("num",-1));
                        imageView.setImageResource(R.mipmap.selecttr);
                        mImgList.add(mAdapter.getItem(position).toString());
                    }
                } else {
                    if (mImgList.contains( mAdapter.getItem(position))) {
                        mImgList.remove( mAdapter.getItem(position));
                        num--;
                        mText.setText("完成" + num + "/" + getIntent().getIntExtra("num",-1));
                        imageView.setImageResource(R.mipmap.selectfa);
                    } else {
                        Toast.makeText(SelectImgActivity.this, "亲,最多选择"+getIntent().getIntExtra("num",-1)+"张图片哦", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void selectImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (cursor.moveToNext()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String imgMsg = cursor.getString(index);
                    mList.add(imgMsg);
                    Log.e("路径", imgMsg);

                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //不论点击返回还是完成都需要将文件传到上个活动
        if (id == mBack.getId()) {
            Intent intent=getIntent();
           intent.putStringArrayListExtra("imgList",mImgList);
            setResult(RESULT_OK,intent);
            finish();
        }else if (id==mText.getId()){
            Intent intent=getIntent();
            intent.putStringArrayListExtra("imgList",mImgList);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
