package com.chengfan.xiyou.ui.accompany;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyUserInfoContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyUserInfoPresenterImpl;
import com.chengfan.xiyou.ui.adapter.AccompanyUserInfoAdapter;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.ui.dialog.MemberShipDialog;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.DataFormatUtil;
import com.chengfan.xiyou.utils.MyToastUtil;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.chengfan.xiyou.view.WrapContentHeightViewPager;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.navigation.BottomNavigationViewEx;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/12:53
 * @Description: 个人详情
 */
public class AccompanyUserInfoActivity extends
        BaseActivity<AccompanyUserInfoContract.View, AccompanyUserInfoPresenterImpl>
        implements AccompanyUserInfoContract.View {
    @BindView(R.id.a_user_info_pic_iv)
    ImageView mAUserInfoPicIv;
    @BindView(R.id.a_user_info_name_tv)
    MediumTextView mAUserInfoNameTv;
    @BindView(R.id.a_user_info_hy_iv)
    ImageView mAUserInfoHyIv;
    @BindView(R.id.a_user_info_sex_iv)
    ImageView mAUserInfoSexIv;
    @BindView(R.id.a_user_info_age_num_tv)
    RegularTextView mAUserInfoAgeNumTv;
    @BindView(R.id.a_user_info_lick_iv)
    ImageView mAUserInfoLickIv;
    @BindView(R.id.a_user_info_lick_tv)
    RegularTextView mAUserInfoLickTv;
    @BindView(R.id.a_user_info_lick_ll)
    LinearLayout mAUserInfoLickLl;
    @BindView(R.id.a_user_info_game_select_rv)
    RecyclerView mAUserInfoGameSelectRv;
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    WrapContentHeightViewPager mFragmentNavigationVp;

    @BindView(R.id.a_user_info_game_select_rl)
    RelativeLayout rl_accompany;
    @BindView(R.id.tv_chat_accompany)
    TextView tv_chat;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    AccompanyDataInfoFragment mAccompanyDataInfoFragment;
    AccompanyDynamicFragment mAccompanyDynamicFragment;

    Bundle revBundle;
    int currentMemberId;

    AccompanyUserInfoEntity mAccompanyUserInfoEntity;
    MemberShipDialog mMemberShipDialog;
    AccompanyUserInfoAdapter mUserInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_user_info);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .applyNavigation(false)
                .statusDark(true)
                .create()
                .immersionBar();

        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            currentMemberId = revBundle.getInt(APPContents.E_CURRENT_MEMBER_ID);
            APPContents.E_CURRENT_MEMBER_IDS = currentMemberId;
        }
        mBotNav.setTypeface(Typeface.createFromAsset(AccompanyUserInfoActivity.this.getAssets(), APPContents.FONTS_BOLD));

        mMemberShipDialog = new MemberShipDialog(this);

        mAccompanyUserInfoEntity = new AccompanyUserInfoEntity();
        mUserInfoAdapter = new AccompanyUserInfoAdapter(R.layout.adapter_search_game, mAccompanyUserInfoEntity.getAccompanyPlay());
        mAUserInfoGameSelectRv.setLayoutManager(new LinearLayoutManager(this));
        mAUserInfoGameSelectRv.setAdapter(mUserInfoAdapter);

        mUserInfoAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mAccompanyUserInfoEntity.getAccompanyPlay().get(position).getId());
                ForwardUtil.getInstance(AccompanyUserInfoActivity.this).forward(AccompanyDetailActivity.class, toBundle);
            }

        });

        mPresenter.userInfoParameter(currentMemberId);
    }

    @OnClick({R.id.detail_back, R.id.a_user_info_more_tv, R.id.tv_chat_accompany})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.a_user_info_more_tv:
                ForwardUtil.getInstance(this).forward(AccompanyMoreActivity.class);
                break;
            case R.id.tv_chat_accompany:
                RongIM.getInstance().startPrivateChat(AccompanyUserInfoActivity.this,
                        String.valueOf(currentMemberId),
                        mAccompanyUserInfoEntity.getNickname());
                break;
        }
    }

    @Override
    public void userInfoLoad(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        mAccompanyUserInfoEntity = accompanyUserInfoEntity;

        bottomInit(mAccompanyUserInfoEntity);
        initView(mAccompanyUserInfoEntity);
    }


    @Override
    public void memberShipLoad(BaseApiResponse baseApiResponse, boolean isLike) {
        if (baseApiResponse.isSuc()) {
            if (isLike) {
                mAUserInfoLickLl.setBackgroundResource(R.drawable.user_licked_bg);
                mAUserInfoLickIv.setImageResource(R.drawable.icon_licked);
                mAUserInfoLickTv.setText("已关注");
                mAccompanyUserInfoEntity.setIsFans(true);
            } else {
                mAUserInfoLickLl.setBackgroundResource(R.drawable.user_lick_add);
                mAUserInfoLickIv.setImageResource(R.drawable.icon_lick_add);
                mAUserInfoLickTv.setText("关注");
                mAccompanyUserInfoEntity.setIsFans(false);
            }
        } else {
            MyToastUtil.showShortToast(AccompanyUserInfoActivity.this, baseApiResponse.getMsg());
        }
    }

    private void bottomInit(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        fragments = new ArrayList<>(2);
        mAccompanyDynamicFragment = AccompanyDynamicFragment.getInstance(accompanyUserInfoEntity);
        mAccompanyDataInfoFragment = AccompanyDataInfoFragment.getInstance(accompanyUserInfoEntity);

        // add to fragments for adapter
        fragments.add(mAccompanyDynamicFragment);
        fragments.add(mAccompanyDataInfoFragment);

        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.enableAnimation(true);

        mBotNav.setLargeTextSize(20);
        mBotNav.setSmallTextSize(15);
        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setOffscreenPageLimit(0);
        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);
    }

    private void initView(final AccompanyUserInfoEntity accompanyUserInfoEntity) {
        ImageLoaderManager.getInstance().showImage(mAUserInfoPicIv, APIContents.HOST + "/" + accompanyUserInfoEntity.getAvatarUrl());
        mAUserInfoNameTv.setText(accompanyUserInfoEntity.getNickname());
        mAUserInfoAgeNumTv.setText(accompanyUserInfoEntity.getAge() + "岁 | " + accompanyUserInfoEntity.getTotalFans() + "粉丝");

        if (accompanyUserInfoEntity.getId() ==
                DataFormatUtil.stringToInt(AppData.getString(AppData.Keys.AD_USER_ID))) {
            mAUserInfoLickLl.setVisibility(View.GONE);
            tv_chat.setVisibility(View.GONE);
        } else {
            tv_chat.setVisibility(View.VISIBLE);
            if (accompanyUserInfoEntity.isIsFans()) {
                mAUserInfoLickLl.setBackgroundResource(R.drawable.user_licked_bg);
                mAUserInfoLickIv.setImageResource(R.drawable.icon_licked);
                mAUserInfoLickTv.setText("已关注");
            } else {
                mAUserInfoLickLl.setBackgroundResource(R.drawable.user_lick_add);
                mAUserInfoLickIv.setImageResource(R.drawable.icon_lick_add);
                mAUserInfoLickTv.setText("关注");
            }
        }

        if (mAccompanyUserInfoEntity.getGender() == 1) {
            mAUserInfoSexIv.setImageResource(R.drawable.home_nan);
        } else {
            mAUserInfoSexIv.setImageResource(R.drawable.home_nv);
        }

        //mMemberShipDialog.setTitle(accompanyUserInfoEntity.getNickname());

        mMemberShipDialog.setMemberShipListener(new MemberShipDialog.MemberShipListener() {
            @Override
            public void onMemberShipListener() {
                mPresenter.memberShipLoad(currentMemberId, false);
            }
        });
        mAUserInfoLickLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accompanyUserInfoEntity.isIsFans()) {
                    mMemberShipDialog.setTitle(mAccompanyUserInfoEntity.getNickname());
                } else {
                    mPresenter.memberShipLoad(currentMemberId, true);

                }
            }
        });

        mUserInfoAdapter.setNewData(accompanyUserInfoEntity.getAccompanyPlay());
        if (accompanyUserInfoEntity.getAccompanyPlay().isEmpty()) {
            rl_accompany.setVisibility(View.GONE);
        } else {
            rl_accompany.setVisibility(View.VISIBLE);
        }
    }

}
