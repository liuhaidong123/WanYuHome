package com.home.wanyu.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.wanyu.HttpUtils.HttpTools;
import com.home.wanyu.R;
import com.home.wanyu.apater.CommercialAddressHositoryAda;
import com.home.wanyu.apater.ShoppingSearchAda;
import com.home.wanyu.bean.Hository;
import com.home.wanyu.bean.getCircleCommentMsg.LikeNum;
import com.home.wanyu.bean.shoppingSearchAddress.Result;
import com.home.wanyu.bean.shoppingSearchAddress.Root;
import com.home.wanyu.myUtils.SearchAddressDB;

import java.util.ArrayList;
import java.util.List;

public class CommercialSearchAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private EditText mEdit_content;


    private ImageView delete_img;
    private ListView mListview, mSearchListview;
    private List<Hository> mList = new ArrayList<>();
    private List<Result> mSearchList = new ArrayList<>();
    private CommercialAddressHositoryAda mAdapter;
    private ShoppingSearchAda mSearchAda;
    private SearchAddressDB mMyDB;
    private SQLiteDatabase mSQLite;

    private RelativeLayout mdelete_rl;
    private HttpTools mHttptools;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 135) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getResult() != null) {
                        if (root.getResult().size() == 0) {
                            Toast.makeText(CommercialSearchAddressActivity.this, "亲，没有您想到的地址哦", Toast.LENGTH_SHORT).show();
                        } else {
                            mdelete_rl.setVisibility(View.GONE);
                            mListview.setVisibility(View.GONE);
                            mSearchListview.setVisibility(View.VISIBLE);
                            mSearchList = root.getResult();
                            mSearchAda = new ShoppingSearchAda(CommercialSearchAddressActivity.this, mSearchList);
                            mSearchListview.setAdapter(mSearchAda);
                        }
                    } else {
                        Toast.makeText(CommercialSearchAddressActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_search_address);
        mHttptools = HttpTools.getHttpToolsInstance();
        initView();
    }

    private void initView() {
        mMyDB = new SearchAddressDB(this, "address_db", null, 1);
        mSQLite = mMyDB.getWritableDatabase();

        //查看address表中所有数据
        Cursor cursor = mSQLite.rawQuery("select * from address order by _id desc", null);
        while (cursor.moveToNext()) {
            String addressName = cursor.getString(cursor.getColumnIndex("addressName"));
            Log.e("历史地址：", "------------" + addressName);
            mList.add(new Hository(addressName));
        }
        cursor.close();

        mBack = (ImageView) findViewById(R.id.search_Address_back);
        mBack.setOnClickListener(this);

        //搜索内容
        mEdit_content = (EditText) findViewById(R.id.search_edit);
        mEdit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //按下的时候会会执行：手指按下和手指松开俩个过程
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //do something;
                        if (!getContent().equals("")) {
                            //请求数据
                            mHttptools.shoppingSearchAddress(mhandler, getContent());
                            //将搜索的内容存到数据库中
                            ContentValues valuesDrug = new ContentValues();
                            valuesDrug.put("addressName", getContent());
                            mSQLite.insert("address", null, valuesDrug);
                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            Toast.makeText(CommercialSearchAddressActivity.this, "请输入地址哦", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return true;
                }
                return false;
            }
        });

        mEdit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count==0){
                        mdelete_rl.setVisibility(View.VISIBLE);
                        mListview.setVisibility(View.VISIBLE);
                        mSearchListview.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //删除记录
        mdelete_rl = (RelativeLayout) findViewById(R.id.delete_rl);
        delete_img = (ImageView) findViewById(R.id.delete);
        delete_img.setOnClickListener(this);
        //搜索到小区listview
        mSearchListview = (ListView) findViewById(R.id.search_listview);
        mSearchListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=getIntent();
                intent.putExtra("name",mSearchList.get(position).getRname());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        // 记录列表
        mListview = (ListView) findViewById(R.id.hos_listview);
        mAdapter = new CommercialAddressHositoryAda(this, mList);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //调用接口搜索
                mEdit_content.setText(mList.get(position).getAddress());
                //请求数据
                mHttptools.shoppingSearchAddress(mhandler, mList.get(position).getAddress());
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBack.getId()) {
            finish();
        } else if (id == delete_img.getId()) {//删除记录
            mSQLite.execSQL("delete from address");
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }

    }


    public String getContent() {
        if (mEdit_content.getText().toString().trim().equals("")) {
            return "";
        }
        return mEdit_content.getText().toString().trim();
    }
}
