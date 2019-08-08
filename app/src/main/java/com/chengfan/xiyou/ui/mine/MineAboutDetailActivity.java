package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-09/11:34
 * @Description: 公司介绍
 */
public class MineAboutDetailActivity extends BaseActivity {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_about_detail);
        ButterKnife.bind(this);

        mXyMiddleTv.setText(getResources().getText(R.string.about_title_txt));

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
