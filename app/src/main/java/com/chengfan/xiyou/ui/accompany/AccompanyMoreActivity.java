package com.chengfan.xiyou.ui.accompany;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyMoreContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.chengfan.xiyou.domain.model.entity.AccompanyMoreEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyMorePresenterImpl;
import com.chengfan.xiyou.ui.adapter.AccompanyMoreAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/16:11
 * @Description: 更多陪玩
 */
public class AccompanyMoreActivity extends BaseActivity<AccompanyMoreContract.View, AccompanyMorePresenterImpl> implements AccompanyMoreContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.ap_dynamic_rv)
    RecyclerView mApDynamicRv;
    @BindView(R.id.ap_more_zrl)
    ZRefreshLayout mApMoreZrl;
    List<AccompanyMoreEntity> mAccompanyMoreEntityList;
    View emptyView;

    AccompanyMoreAdapter mAccompanyMoreAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_more);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText("更多陪玩");
        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mApDynamicRv.getParent(), false);

        mAccompanyMoreEntityList = new ArrayList<>();
        mAccompanyMoreAdapter = new AccompanyMoreAdapter(R.layout.adapter_search_game, mAccompanyMoreEntityList);
        mApDynamicRv.setLayoutManager(new LinearLayoutManager(this));
        mApDynamicRv.setAdapter(mAccompanyMoreAdapter);

        mAccompanyMoreAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mAccompanyMoreEntityList.get(position).getId());
                ForwardUtil.getInstance(AccompanyMoreActivity.this).forward(AccompanyDetailActivity.class, toBundle);
            }
        });

        mPresenter.moreParameter();
    }

    @Override
    public void moreLoad(List<AccompanyMoreEntity> accompanyMoreEntityList) {
        if (accompanyMoreEntityList.size() < 1) {
            mAccompanyMoreAdapter.setEmptyView(emptyView);
        } else {
            mAccompanyMoreEntityList = accompanyMoreEntityList;
            mAccompanyMoreAdapter.setNewData(mAccompanyMoreEntityList);
        }
    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }


}
