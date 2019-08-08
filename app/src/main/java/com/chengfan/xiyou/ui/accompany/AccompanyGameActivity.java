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

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyGameContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyGamePresenterImpl;
import com.chengfan.xiyou.ui.adapter.AccompanyGameAdapter;
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
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
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
 * @DATE : 2019-07-04/16:10
 * @Description: 游戏陪玩
 */
public class AccompanyGameActivity extends BaseActivity<AccompanyGameContract.View, AccompanyGamePresenterImpl> implements AccompanyGameContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.accompany_game_user_rv)
    RecyclerView mAccompanyGameUserRv;
    @BindView(R.id.accompany_game_zrl)
    ZRefreshLayout mAccompanyGameZrl;

    AccompanyGameAdapter mAccompanyGameAdapter;
    List<AccompanyGameEntity> mAccompanyGameEntityList;

    Bundle revBundle;
    String subjectId;
    View emptyView;
    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_game);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.main_pei_game_txt));

        revBundle = getIntent().getExtras();
        if (revBundle != null)
            subjectId = revBundle.getString(APPContents.E_SUBJECT_ID);
        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mAccompanyGameUserRv.getParent(), false);

        Logger.d("AccompanyGameActivity ===>>>  subject id : " + subjectId);
        mPresenter.gameParameter(subjectId, 1, true);


        mAccompanyGameEntityList = new ArrayList<>();
        mAccompanyGameAdapter = new AccompanyGameAdapter(R.layout.adapter_accompany_game, mAccompanyGameEntityList);
        mAccompanyGameUserRv.setLayoutManager(new LinearLayoutManager(this));
        mAccompanyGameUserRv.setAdapter(mAccompanyGameAdapter);
        mAccompanyGameAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {

                Log.e("onItemClick","position:---"+position);
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mAccompanyGameEntityList.get(position).getId());
                ForwardUtil.getInstance(AccompanyGameActivity.this).forward(AccompanyDetailActivity.class, toBundle);
            }
        });


        initZrl();

    }


    private void initZrl() {
        mAccompanyGameZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAccompanyGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.gameParameter(subjectId, page, false);
                        mAccompanyGameZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mAccompanyGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.gameParameter(subjectId, page, true);
                        mAccompanyGameZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void gameLoad(List<AccompanyGameEntity> gameEntityList, boolean isPtr) {
        if (gameEntityList.size() == 0) {
            mAccompanyGameAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mAccompanyGameAdapter.replaceData(mAccompanyGameEntityList);
                mAccompanyGameEntityList = gameEntityList;
            } else {
                mAccompanyGameEntityList.addAll(gameEntityList);
            }
            mAccompanyGameAdapter.setNewData(mAccompanyGameEntityList);

        }
    }



    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }


}
