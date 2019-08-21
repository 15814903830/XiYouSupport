package com.chengfan.xiyou.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.ui.dynamic.DynamicAttentionFragment;
import com.chengfan.xiyou.ui.dynamic.DynamicIssuedActivity;
import com.chengfan.xiyou.ui.dynamic.DynamicMineFragment;
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
public class DynamicFragment extends BaseFragment {
    View mView;
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;
    Unbinder mUnbinder;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    DynamicMineFragment mDynamicMineFragment;
    DynamicAttentionFragment mDynamicAttentionFragment;
    private boolean data = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, null);
        mUnbinder = ButterKnife.bind(this, mView);
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
        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);


        if (AppData.getBoole(AppData.Keys.AD_IS_SHOW)) {
            mBotNav.setCurrentItem(1);
            AppData.putBoolen(AppData.Keys.AD_IS_SHOW, false);
        }
    }

    @OnClick(R.id.dynamic_go_issued_iv)
    public void onClick() {
        ForwardUtil.getInstance(getActivity()).forward(DynamicIssuedActivity.class);
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
}
