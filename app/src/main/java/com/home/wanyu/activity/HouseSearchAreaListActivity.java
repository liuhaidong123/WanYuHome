package com.home.wanyu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.R;
import com.home.wanyu.apater.HouseMsgListAda;
import com.home.wanyu.myUtils.ImgUitls;
import com.home.wanyu.myUtils.SearchAreaDB;
import com.home.wanyu.myview.MyListView;

public class HouseSearchAreaListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private EditText mEdit;

    private MyListView myListView;
    private HouseMsgListAda msgListAda;

    private SearchAreaDB mAreaDB;
    private SQLiteDatabase mAreaSqliteDB;

    private ImageView mPost_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_search_area_list);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.house_area_back);
        mBack.setOnClickListener(this);

        mEdit = (EditText) findViewById(R.id.input_area_edit);
        mEdit.setText(getIntent().getStringExtra("searchArea"));
        mEdit.setSelection(mEdit.getText().length());
        //小区户型数据库
        mAreaDB = new SearchAreaDB(this, "area_db", null, 1);
        mAreaSqliteDB = mAreaDB.getWritableDatabase();

        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                            @Override
                                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                                                    //按下的时候会执行：手指按下和手指松开俩个过程
                                                    if (event.getAction() == KeyEvent.ACTION_UP) {
                                                        //do something;

                                                        if (!getContent().equals("")) {
                                                            //将搜索的内容存到数据库中，如果搜索的是小区，搜索到以后跳转到小区页面HouseSearchAreaListActivity
                                                            ContentValues valuesDrug = new ContentValues();
                                                            valuesDrug.put("areaname", getContent());
                                                            mAreaSqliteDB.insert("area", null, valuesDrug);

                                                        } else {
                                                            Toast.makeText(HouseSearchAreaListActivity.this, "请输入小区或户型哦", Toast.LENGTH_SHORT).show();
                                                        }
                                                        //隐藏软键盘
                                                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                    }
                                                    return true;
                                                }

                                                return false;
                                            }

                                        }

        );

        //小区房屋列表
        myListView= (MyListView) findViewById(R.id.house_area_refresh_listview);
        msgListAda=new HouseMsgListAda(this);
        myListView.setAdapter(msgListAda);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HouseSearchAreaListActivity.this,HouseLookMessageActivity.class);
                startActivity(intent);
            }
        });

        //发帖
        mPost_card= (ImageView) findViewById(R.id.area_post_card);
        mPost_card.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }else if (id==mPost_card.getId()){
            startActivity(new Intent(this,HousePostCardActivity.class));
        }
    }

    public String getContent() {
        if (mEdit.getText().toString().trim().equals("")) {
            return "";
        }
        return mEdit.getText().toString().trim();
    }
}
