package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineFamilyContract;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyMemberEntity;
import com.chengfan.xiyou.domain.presenter.MineFamilyPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MineFamilyAdapter;
import com.chengfan.xiyou.ui.adapter.ViewPagerDialogAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
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
 * @DATE : 2019-07-06/13:04
 * @Description: 我的家族
 */
public class MineFamilyActivity extends BaseActivity<MineFamilyContract.View, MineFamilyPresenterImpl> implements MineFamilyContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;

    @BindView(R.id.family_name_tv)
    TextView mFamilyNameTv;
    @BindView(R.id.family_pic_civ)
    CircleImageView mFamilyPicCiv;
    @BindView(R.id.family_user_name_tv)
    TextView mFamilyUserNameTv;
    @BindView(R.id.family_create_time_tv)
    TextView mFamilyCreateTimeTv;
    @BindView(R.id.family_num_tv)
    TextView mFamilyNumTv;
    @BindView(R.id.family_rv)
    RecyclerView mFamilyRv;
    @BindView(R.id.family_zrl)
    ZRefreshLayout mFamilyZrl;
    @BindView(R.id.family_nsv)
    NestedScrollView mFamilyNsv;
    @BindView(R.id.empty_ll)
    LinearLayout mEmptyLl;
    @BindView(R.id.family_vp)
    JazzyViewPager mFamilyVp;
    @BindView(R.id.img_num_tv)
    TextView mImgNumTv;
    @BindView(R.id.family_des_tv)
    TextView mFamilyDesTv;

    List<ImageEntity> mImageEntityList;
    ViewPagerDialogAdapter viewPagerAdapter;
    int mImageUrlsSize;

    List<MineFamilyMemberEntity> mMineFamilyMemberEntityList;
    MineFamilyAdapter mMineFamilyAdapter;

    MineFamilyEntity mineFamilyEntity;
    int page = 1;

    boolean isCreateNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_family);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.family_title_txt));


        mineFamilyEntity = new MineFamilyEntity();
        mMineFamilyMemberEntityList = new ArrayList<>();
        mMineFamilyAdapter = new MineFamilyAdapter(R.layout.adapter_mine_family, mMineFamilyMemberEntityList);
        mFamilyRv.setLayoutManager(new LinearLayoutManager(this));
        mFamilyRv.setAdapter(mMineFamilyAdapter);

        mPresenter.familyParameter();

        initZrl();
    }

    @Override
    public void familyLoad(MineFamilyEntity familyEntity) {
        if (familyEntity != null) {
            mineFamilyEntity = familyEntity;
            isCreateNew = false;
            mXyMoreTv.setText(getResources().getText(R.string.family_compile_txt));
            mEmptyLl.setVisibility(View.GONE);
            mFamilyNsv.setVisibility(View.VISIBLE);
            initView(mineFamilyEntity);

            mPresenter.familyMemberParameter(mineFamilyEntity.getId(), 1, true);
        }
    }

    @Override
    public void familyErrorLoad() {
        mXyMoreTv.setText(getResources().getText(R.string.family_create_txt));
        mFamilyNsv.setVisibility(View.GONE);
        mEmptyLl.setVisibility(View.VISIBLE);
        isCreateNew = true;
    }

    @Override
    public void familyMemberLoad(List<MineFamilyMemberEntity> familyMemberEntityList, boolean isPtr) {
        if (familyMemberEntityList.size() > 0) {
            if (isPtr) {
                mMineFamilyMemberEntityList = familyMemberEntityList;
            } else {
                mMineFamilyMemberEntityList.addAll(familyMemberEntityList);
            }
            mMineFamilyAdapter.setNewData(mMineFamilyMemberEntityList);
            mFamilyNumTv.setText("家族成员(" + mMineFamilyMemberEntityList.size() + ")");
        }
    }

    private void initView(MineFamilyEntity familyEntity) {

        mFamilyNameTv.setText(familyEntity.getName());
        mFamilyDesTv.setText(familyEntity.getDescribe());
        mFamilyCreateTimeTv.setText(familyEntity.getCreateTime());
        mFamilyUserNameTv.setText(familyEntity.getMember().getNickname());
        ImageLoaderManager.getInstance().showImage(mFamilyPicCiv, APIContents.HOST + "/" + familyEntity.getMember().getAvatarUrl());

        mImageEntityList = new ArrayList<>();
        String imageStr = mineFamilyEntity.getBanner();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\;");
            for (String str : strArr) {
                Logger.d(APIContents.HOST + str);
                mImageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
            }
        }
        initImageToViewPager(mImageEntityList);
    }

    private void initZrl() {
        mFamilyZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mFamilyZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.familyMemberParameter(mineFamilyEntity.getId(), page, false);
                        mFamilyZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mFamilyZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        page = 1;
                        mPresenter.familyMemberParameter(mineFamilyEntity.getId(), page, true);
                        mFamilyZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    private void initImageToViewPager(final List<ImageEntity> list) {
        if (list != null)
            mImageUrlsSize = list.size();
        for (int i = 0; i < mImageUrlsSize; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15, 15);
            lp.setMargins(10, 0, 10, 0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        mFamilyVp.setTransitionEffect(JazzyViewPager.TransitionEffect.Standard);

        viewPagerAdapter = new ViewPagerDialogAdapter(this, list, mFamilyVp);
        mFamilyVp.setAdapter(viewPagerAdapter);
        mFamilyVp.setOnPageChangeListener(new MyPageChangeListener());

        mFamilyVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mImageUrlsSize == 0 || mImageUrlsSize == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(final int position) {
            mImgNumTv.setText(position + 1 + " / " + mImageEntityList.size());
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                Bundle toBundle = new Bundle();
                if (isCreateNew)
                    toBundle.putBoolean(APPContents.BUNDLE_BOOLEAN, true);
                else {
                    toBundle.putBoolean(APPContents.BUNDLE_BOOLEAN, false);
                    AppData.putObject(AppData.Keys.AD_FAMILY_OBJECT, mineFamilyEntity);
                }
                ForwardUtil.getInstance(this).forward(MineCrateFamilyActivity.class, toBundle);
                Logger.d("MineFamilyActivity ===<>>>  isCreateNew " + isCreateNew);
                break;
        }
    }


}
