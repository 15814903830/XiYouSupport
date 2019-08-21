package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.ui.mine.order.MinePlaceOrderFragment;
import com.chengfan.xiyou.ui.mine.order.MineTakingOrderFragment;
import com.chengfan.xiyou.utils.AppData;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.navigation.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/12:56
 * @Description: 我的订单
 * /api/AccompanyPlay/MyCreateOrder
 */
public class MineOrderActivity extends BaseActivity {
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    MinePlaceOrderFragment mMinePlaceOrderFragment;
    MineTakingOrderFragment mMineTakingOrderFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mBotNav.setTypeface(Typeface.createFromAsset(this.getAssets(), APPContents.FONTS_MEDIUM));
        bottomInit();
    }


    private void bottomInit() {
        fragments = new ArrayList<>(2);
        mMinePlaceOrderFragment = new MinePlaceOrderFragment();
        mMineTakingOrderFragment = new MineTakingOrderFragment();

        // add to fragments for adapter
        fragments.add(mMinePlaceOrderFragment);
        fragments.add(mMineTakingOrderFragment);

        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.setLargeTextSize(20);
        mBotNav.setSmallTextSize(15);
        mBotNav.setIconSize(0, 0);
        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setOffscreenPageLimit(2);
        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);
        mBotNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == 0) {
                    AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, true);
                } else {
                    AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, false);
                }
                return true;
            }
        });

    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
