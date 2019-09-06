package com.chengfan.xiyou.ui.accompany;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ApplyLableListBean;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.main.LableAdapter2;
import com.chengfan.xiyou.ui.main.LableAdapter3;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多陪玩
 */
public class AccomPanyActivity extends AppCompatActivity implements HttpCallBack {

    private HttpCallBack mHttpCallBack;
    private ImageView tvFanghui;
    private RecyclerView recyclerView;
    LableAdapter3 lableAdapter3;
    private List<AccomLable2Base> lable2Bases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accom_pany);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        mHttpCallBack=this;
        //AppData.getString(AppData.Keys.AD_USER_ID)
        getdata();
    }

    private void initView() {
        tvFanghui =  findViewById(R.id.tv_fanghui);
        recyclerView =findViewById(R.id.rv_genduo);
        tvFanghui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet(APIContents.HOST +"/api/AccompanyPlay/AccompanyPlaySubject",mHttpCallBack,0);
            }
        }).start();
    }

    private void initlable(List<AccomLable2Base> list) {
        try {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            recyclerView.setLayoutManager(layoutManager);
                lableAdapter3 = new LableAdapter3(this, list);
                recyclerView.setAdapter(lableAdapter3);
                lableAdapter3.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("responselable2Bases",response);
        switch (requestId){
            case 0:
                Log.e("responselableJSON",response);
                lable2Bases= JSON.parseArray(response,AccomLable2Base.class);
                Log.e("responselableJSON",lable2Bases.get(0).getTitle());
                initlable(lable2Bases);
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

}
