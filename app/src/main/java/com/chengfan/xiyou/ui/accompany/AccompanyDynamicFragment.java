package com.chengfan.xiyou.ui.accompany;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.adapter.AccompanyDynamicAdapter;
import com.zero.ci.base.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/14:52
 * @Description: 个人动态
 */
public class AccompanyDynamicFragment extends BaseFragment implements Myforlike, HttpCallBack {
    View mView;
    @BindView(R.id.ap_dynamic_rv)
    RecyclerView mApDynamicRv;
    Unbinder mUnbinder;

    HttpCallBack mHttpCallBack;
    AccompanyDynamicAdapter mAccompanyDynamicAdapter;

    AccompanyUserInfoEntity mAccompanyUserInfoEntity;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_accompany_dynamic, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mHttpCallBack=this;
        mAccompanyUserInfoEntity = new AccompanyUserInfoEntity();

        Bundle arguments = this.getArguments();
        mAccompanyUserInfoEntity = (AccompanyUserInfoEntity) arguments.getSerializable(APPContents.BUNDLE_FRAGMENT);

        if (mAccompanyUserInfoEntity != null) {
            mAccompanyDynamicAdapter = new AccompanyDynamicAdapter(R.layout.adapter_accompany_dynamic, mAccompanyUserInfoEntity.getMemberNews(),this);
            mApDynamicRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mApDynamicRv.setAdapter(mAccompanyDynamicAdapter);
            mApDynamicRv.setFocusable(false);
        }
        return mView;
    }


    public static AccompanyDynamicFragment getInstance(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        AccompanyDynamicFragment newsFragment = new AccompanyDynamicFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(APPContents.BUNDLE_FRAGMENT, accompanyUserInfoEntity);
        newsFragment.setArguments(bundle);
        return newsFragment;

    }

    @Override
    public void mylike(int memberId, int memberNewsId) {
        setlike(memberId,memberNewsId);
    }

    @Override
    public void onResponse(String response, int requestId) {

    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {

        switch (requestId) {
            case 0://点赞

                break;

        }
    }

    private void setlike(final int memberId, final int memberNewsId ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("memberId", ""+memberId);
                    jsonObject.put("memberNewsId", ""+memberNewsId);
                    OkHttpUtils.doPostJson(APIContents.SendLIKEr, jsonObject.toString(), mHttpCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
