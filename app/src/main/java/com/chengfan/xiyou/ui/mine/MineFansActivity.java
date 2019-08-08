package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/12:39
 * @Description: 粉丝  ====》》》加载网页
 */
public class MineFansActivity extends BaseActivity {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.fans_wv)
    WebView mFansWv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_fans);
        ButterKnife.bind(this);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();


        mXyMiddleTv.setText(getResources().getText(R.string.fans_title_txt));

        mFansWv.loadUrl("http://xy.gx11.cn/Wap/Fans?id=" + AppData.getString(AppData.Keys.AD_USER_ID) + "&page=1&limit=0");

    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
