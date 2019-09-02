package com.chengfan.xiyou.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineGameContract;
import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.chengfan.xiyou.domain.presenter.MineGamePresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.accompany.AccompanyDetailActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.MineGameAdapter;
import com.chengfan.xiyou.ui.complete.CompleteVideoActivity;
import com.chengfan.xiyou.ui.mine.order.AutonymActivity;
import com.chengfan.xiyou.ui.mine.order.MinEBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.tool.ForwardUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/12:57
 * @Description: 我的陪玩
 */
public class MineGameActivity extends BaseActivity<MineGameContract.View, MineGamePresenterImpl> implements MineGameContract.View, HttpCallBack {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.mine_game_rv)
    RecyclerView mMineGameRv;
    private MinEBase minEBase;
    List<MemberIdEntity> mMemberIdEntityList;
    MineGameAdapter mMineGameAdapter;
    private HttpCallBack mHttpCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_game);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mHttpCallBack=this;
        mXyMiddleTv.setText(getResources().getText(R.string.game_title_txt));
        mXyMoreTv.setText(getResources().getText(R.string.game_add_txt));
        mXyMoreTv.setTextColor(getResources().getColor(R.color.color_EF5350));
        mXyMoreTv.setTextSize(15);

        mMemberIdEntityList = new ArrayList<>();
        mMineGameAdapter = new MineGameAdapter(R.layout.adapter_mine_game, mMemberIdEntityList);
        mMineGameRv.setLayoutManager(new LinearLayoutManager(this));
        mMineGameRv.setAdapter(mMineGameAdapter);

        mMineGameAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Intent intent=new Intent(MineGameActivity.this,MineAddPlayActivity2.class);
                intent.putExtra("currentMemberId",""+mMemberIdEntityList.get(position).getId());
                startActivity(intent);
            }
        });

        mPresenter.gamePlayParameter();
    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                getdata();//验证认证信息是否完善
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.gamePlayParameter();
    }

    @Override
    public void gamePlayLoad(List<MemberIdEntity> memberIdEntityList) {
        if (memberIdEntityList.size() > 0) {
            mMineGameAdapter.replaceData(mMemberIdEntityList);
            mMemberIdEntityList = memberIdEntityList;
            mMineGameAdapter.setNewData(mMemberIdEntityList);
        }
    }
    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("idrun", AppData.getString(AppData.Keys.AD_USER_ID));
                OkHttpUtils.doGet(APIContents.HOST + "/api/Account/GetAccount/" + AppData.getString(AppData.Keys.AD_USER_ID), mHttpCallBack, 1);
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandlere = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandlere.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandlere.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        minEBase = JSON.parseObject(response, MinEBase.class);
        if (minEBase.getRealNameTag().equals("未认证")){
            Toast.makeText(this, "请完成实名认证", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AutonymActivity.class));
        }else if (minEBase.getVerificationGenderStatusTag().equals("未认证")){
            Toast.makeText(this, "请完成视频认证", Toast.LENGTH_SHORT).show();
            Bundle toBundle = new Bundle();
            toBundle.putBoolean(APPContents.BUNDLE_BOOLEAN, true);
            ForwardUtil.getInstance(this).forward(CompleteVideoActivity.class, toBundle);
        }else if(minEBase.getRealNameTag().equals("已认证")&minEBase.getVerificationGenderStatusTag().equals("已认证")){
            ForwardUtil.getInstance(this).forward(MineAddPlayActivity.class);//跳转发布陪玩
        }else {
            Toast.makeText(this, "请等待审核结果", Toast.LENGTH_SHORT).show();
        }
    }
}
