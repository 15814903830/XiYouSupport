package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
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
 * @DATE : 2019-07-06/13:07
 * @Description: 关于我们
 */
public class MineAboutActivity extends BaseActivity {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.about_wv)
    WebView mAboutWv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_about);
        ButterKnife.bind(this);
        mXyMiddleTv.setText(getResources().getText(R.string.about_title_txt));

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mAboutWv.loadUrl("http://xy.gx11.cn/WapNews/Detail/4");
    }

    @OnClick({R.id.xy_back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
        }
    }
}
