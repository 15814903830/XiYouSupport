package com.chengfan.xiyou.ui.chat;

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
 * @DATE : 2019-07-09/16:48
 * @Description: 家族专区====》》》 家族网页地址
 */
public class ChatFamilyActivity extends BaseActivity {


    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.chat_family_wv)
    WebView mChatFamilyWv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_family);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText("家族专区");
        mChatFamilyWv.loadUrl("http://xy.gx11.cn/Wap/Family?id=" + AppData.getString(AppData.Keys.AD_USER_ID) + "&sort=0&page=1&limit=0");
    }


    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
