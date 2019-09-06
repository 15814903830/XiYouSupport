package com.chengfan.xiyou.ui.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.utils.AppData;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AddcontexActivity extends AppCompatActivity{

    private HttpCallBack mHttpCallBack;
    ListView mlistview;
    private Mylistviewaparter mylistviewaparter;
    private List<AddTextBase> list;
    private ImageView tv_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontex);
       // mHttpCallBack=this;
        mlistview=findViewById(R.id.my_listview);
        tv_return=findViewById(R.id.tv_return);

        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.e("listsssss","----"+getIntent().getStringExtra("list"));
                try {
            list= JSON.parseArray(getIntent().getStringExtra("list"),AddTextBase.class);
            mylistviewaparter=new Mylistviewaparter(this,list);
            mlistview.setAdapter(mylistviewaparter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getdata() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<RequestParams> list=new ArrayList<>();
//                list.add(new RequestParams("memberId", AppData.getString(AppData.Keys.AD_USER_ID)));
//                OkHttpUtils.doGet(APIContents.HOST +"/api/MemberNews/NewsNotice",list,mHttpCallBack,0);
//            }
//        }).start();
//    }

//    @Override
//    public void onResponse(String response, int requestId) {
//        Message message = mHandler.obtainMessage();
//        message.what = requestId;
//        message.obj = response;
//        mHandler.sendMessage(message);
//    }
//
//    @Override
//    public void onHandlerMessageCallback(String response, int requestId) {
//        try {
//            list= JSON.parseArray(response,AddTextBase.class);
//            mylistviewaparter=new Mylistviewaparter(this,list);
//            mlistview.setAdapter(mylistviewaparter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int requestId = msg.what;
//            String response = (String) msg.obj;
//            onHandlerMessageCallback(response, requestId);
//        }
//    };
}
