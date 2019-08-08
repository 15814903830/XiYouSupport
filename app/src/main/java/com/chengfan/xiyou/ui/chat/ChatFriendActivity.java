package com.chengfan.xiyou.ui.chat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.navigation.BottomNavigationViewEx;
import com.zero.ci.tool.ForwardUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/18:49
 * @Description: 好友、群聊
 */
public class ChatFriendActivity extends BaseActivity {
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    ChatFriendFragment mChatFriendFragment;
    ChatGroupFragment mChatGroupFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_firend);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mBotNav.setTypeface(Typeface.createFromAsset(this.getAssets(), APPContents.FONTS_BOLD));

        bottomInit();
    }


    private void bottomInit() {
        fragments = new ArrayList<>(2);
        mChatFriendFragment = new ChatFriendFragment();
        mChatGroupFragment = new ChatGroupFragment();

        // add to fragments for adapter
        fragments.add(mChatFriendFragment);
        fragments.add(mChatGroupFragment);

        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.enableAnimation(true);
        mBotNav.setTextSize(18);
        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setOffscreenPageLimit(3);

        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);

    }


    @OnClick({R.id.xy_back_btn, R.id.xy_more_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_iv:
                ForwardUtil.getInstance(this).forward(ChatCreateGroupActivity.class);
                break;
        }
    }
}
