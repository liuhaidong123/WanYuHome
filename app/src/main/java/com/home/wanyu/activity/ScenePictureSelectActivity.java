package com.home.wanyu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.home.wanyu.Icons.SceneIcons;
import com.home.wanyu.R;
import com.home.wanyu.adapter.ScenePictureSelectAdapter;
import com.home.wanyu.bean.Bean_ScenePicture;
import com.home.wanyu.myUtils.CMyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//情景图标选择
public class ScenePictureSelectActivity extends CMyActivity implements AdapterView.OnItemClickListener{
    final int RESULTCODE=200;
    final String RESULT="name";
    ScenePictureSelectAdapter adapter;
//    int []icons= SceneIcons.SceneIcons;
    List<Bean_ScenePicture> list;
    @BindView(R.id.scenepicture_gridView)GridView scenepicture_gridView;
    int selectPos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_picture_select);
        titleButton= (TextView) findViewById(R.id.titleButton);
        titleTextView= (TextView) findViewById(R.id.titleTextView);
        initTitle("图标选择","确定",false);
        unbinder= ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        list=new ArrayList<>();
        for (int i=0;i<SceneIcons.SceneIcons.length;i++){
            Bean_ScenePicture scene=new Bean_ScenePicture();
            scene.setIcon(SceneIcons.SceneIcons[i]);
            scene.setSelect(false);
            list.add(scene);
        }
        list.get(0).setSelect(true);
        adapter=new ScenePictureSelectAdapter(this,list);
        scenepicture_gridView.setAdapter(adapter);
        scenepicture_gridView.setOnItemClickListener(this);
    }

    //确定按钮
    @Override
    public void Submit(View vi) {
        super.Submit(vi);
        Intent intent=new Intent();
        intent.putExtra(RESULT, selectPos);
        setResult(RESULTCODE,intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectPos!=position){
                list.get(position).setSelect(true);
                list.get(selectPos).setSelect(false);
                selectPos=position;
                adapter.notifyDataSetChanged();
            }
    }
}
