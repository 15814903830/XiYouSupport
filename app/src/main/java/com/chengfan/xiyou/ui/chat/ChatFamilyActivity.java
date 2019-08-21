package com.chengfan.xiyou.ui.chat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
        initWebSettings();
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        initView();
        mXyMiddleTv.setText("家族专区");
        mChatFamilyWv.loadUrl("http://xy.gx11.cn/Wap/Family?id=" + AppData.getString(AppData.Keys.AD_USER_ID) + "&sort=0&page=1&limit=0");
    }
    private void initView() {
        mChatFamilyWv.setWebViewClient(new WebViewClient() {

                                           @Override
                                           public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                               Log.e("urlurl",url);
                                               mChatFamilyWv.loadUrl(url);
                                               return true;
                                           }
                                       }


        );
    }
    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebSettings() {
        WebSettings webSettings = mChatFamilyWv.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);

    }

}
