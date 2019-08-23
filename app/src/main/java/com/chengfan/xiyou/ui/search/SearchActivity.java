package com.chengfan.xiyou.ui.search;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.utils.FontHelper;
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
 * @DATE : 2019-07-04/16:37
 * @Description: 查询
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;
    @BindView(R.id.search_et)
    EditText mSearchEt;
    @BindView(R.id.search_cont_tv)
    TextView mSearchContTv;
    @BindView(R.id.search_show_ll)
    LinearLayout mSearchShowLl;
    @BindView(R.id.view_empty_search)
    View view_empty;

    private VpAdapter adapter;
    private List<Fragment> fragments;
    SearchUserFragment mSearchUserFragment;
    SearchGameFragment mSearchGameFragment;
    SearchFamilyFragment mSearchFamilyFragment;

    String searchStr = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        view_empty.setVisibility(View.VISIBLE);

        FontHelper.applyFont(this, mSearchContTv, APPContents.FONTS_REGULAR);
        mBotNav.setTypeface(Typeface.createFromAsset(this.getAssets(), APPContents.FONTS_MEDIUM));

        initFragment();

        initEvents();
    }

    private void initFragment() {
        fragments = new ArrayList<>(3);
        mSearchUserFragment = SearchUserFragment.getInstance(searchStr);
        mSearchGameFragment = SearchGameFragment.getInstance(searchStr);
        mSearchFamilyFragment = SearchFamilyFragment.getInstance(searchStr);

        // add to fragments for adapter
        fragments.add(mSearchUserFragment);
        fragments.add(mSearchGameFragment);
        fragments.add(mSearchFamilyFragment);

        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.enableAnimation(true);
        mBotNav.setIconVisibility(false);
        mBotNav.setTextSize(16);
        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setOffscreenPageLimit(3);

        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);
    }

    @OnClick(R.id.search_cont_tv)
    public void onClick() {
        finish();
    }

    private void initEvents() {
//        mSearchEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                searchStr = s.toString().trim();
//                bottomInit(searchStr);
//            }
//        });
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    searchStr = v.getText().toString().trim();
                    bottomInit(searchStr);
                }
                return false;
            }
        });
        view_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void bottomInit(String searchStr) {
        if (view_empty.getVisibility() == View.VISIBLE) {
            view_empty.setVisibility(View.GONE);
        }
        mSearchUserFragment.search(searchStr);
        mSearchGameFragment.search(searchStr);
        mSearchFamilyFragment.search(searchStr);
    }
}
