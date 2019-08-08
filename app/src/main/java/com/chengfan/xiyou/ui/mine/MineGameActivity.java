package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineGameContract;
import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.chengfan.xiyou.domain.presenter.MineGamePresenterImpl;
import com.chengfan.xiyou.ui.accompany.AccompanyDetailActivity;
import com.chengfan.xiyou.ui.adapter.MineGameAdapter;
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
public class MineGameActivity extends BaseActivity<MineGameContract.View, MineGamePresenterImpl> implements MineGameContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.mine_game_rv)
    RecyclerView mMineGameRv;

    List<MemberIdEntity> mMemberIdEntityList;
    MineGameAdapter mMineGameAdapter;

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
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mMemberIdEntityList.get(position).getMemberId());
                ForwardUtil.getInstance(MineGameActivity.this).forward(AccompanyDetailActivity.class);
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
                ForwardUtil.getInstance(this).forward(MineAddPlayActivity.class);
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
}
