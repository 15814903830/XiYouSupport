package com.chengfan.xiyou.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.ui.dynamic.DynamicAttentionFragment;
import com.chengfan.xiyou.ui.dynamic.DynamicIssuedActivity;
import com.chengfan.xiyou.ui.dynamic.DynamicMineFragment;
import com.chengfan.xiyou.ui.mine.order.MineOrderDetailActivity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.navigation.BottomNavigationViewEx;
import com.zero.ci.tool.ForwardUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/10:43
 * @Description: 动态
 */
public class DynamicFragment extends BaseFragment implements HttpCallBack {
    View mView;
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;
    Unbinder mUnbinder;
    @BindView(R.id.tv_redtext)
    TextView tv_redtext;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    DynamicMineFragment mDynamicMineFragment;
    DynamicAttentionFragment mDynamicAttentionFragment;
    private HttpCallBack mHttpCallBack;
    private ImageView imageView;
    private boolean data = true;
    private Mylistviewaparter mylistviewaparter;
    private List<AddTextBase> list;
    private String stringlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mHttpCallBack=this;
        getdata();
        mBotNav.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), APPContents.FONTS_BOLD));
        bottomInit();
        return mView;
    }

    private void bottomInit() {
        fragments = new ArrayList<>(2);
        mDynamicAttentionFragment = new DynamicAttentionFragment();
        mDynamicMineFragment = new DynamicMineFragment();

        // add to fragments for adapter
        fragments.add(mDynamicAttentionFragment);
        fragments.add(mDynamicMineFragment);

        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.setLargeTextSize(18);
        mBotNav.setSmallTextSize(15);
        mBotNav.setIconSize(0, 0);

        // set adapter
        adapter = new VpAdapter(getChildFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setCurrentItem(1);
        mBotNav.setCurrentItem(1);
        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);


        if (AppData.getBoole(AppData.Keys.AD_IS_SHOW)) {
            mBotNav.setCurrentItem(1);
            AppData.putBoolen(AppData.Keys.AD_IS_SHOW, false);
        }
    }

    @OnClick({R.id.dynamic_go_issued_iv, R.id.iv_context})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dynamic_go_issued_iv:
                ForwardUtil.getInstance(getActivity()).forward(DynamicIssuedActivity.class);
                break;
            case R.id.iv_context:
                Intent intent=new Intent(getContext(),AddcontexActivity.class);
                intent.putExtra("list",stringlist);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (data) {
//                bottomInit();
//            } else {
//                data = false;
//            }
//        }
    }

    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list=new ArrayList<>();
                list.add(new RequestParams("memberId", AppData.getString(AppData.Keys.AD_USER_ID)));
                OkHttpUtils.doGet(APIContents.HOST +"/api/MemberNews/NewsNotice",list,mHttpCallBack,0);
            }
        }).start();
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
        stringlist=response;
        try {
            list= JSON.parseArray(response,AddTextBase.class);
           for (int i=0;i<list.size();i++){
               if (list.get(i).isStatus()){
                   tv_redtext.setVisibility(View.VISIBLE);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
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
