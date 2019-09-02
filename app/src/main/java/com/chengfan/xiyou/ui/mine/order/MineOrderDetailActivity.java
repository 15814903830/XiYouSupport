package com.chengfan.xiyou.ui.mine.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/23:29
 * @Description: 订单详情
 */
public class MineOrderDetailActivity extends BaseActivity {

    @BindView(R.id.mine_order_detail_wv)
    WebView mMineOrderDetailWv;
    Bundle revBundle;
    int id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        initWebSettings();
        revBundle = getIntent().getExtras();
        if (revBundle != null)
            id = revBundle.getInt(APPContents.E_ID);
        name = revBundle.getString("NAME");
        Log.e("id","---"+id);
        Log.e("idsss","---"+AppData.getString(AppData.Keys.AD_USER_ID));
        initweview();
        mMineOrderDetailWv.loadUrl("http://api.maihui111.com/WapFinance/AccompanyPlayOrderDetail?id=" + id + "&memberId=" + AppData.getString(AppData.Keys.AD_USER_ID));

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebSettings() {
        WebSettings webSettings = mMineOrderDetailWv.getSettings();
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
        webSettings.setAllowUniversalAccessFromFileURLs(true);

    }

    public void initweview(){
        mMineOrderDetailWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("should", url);
                if (url.contains("Api/Member/SendPrivateLetter")){
                    Log.e("toMemberId", url.split("=")[1]);
                }
                RongIM.getInstance().startPrivateChat(MineOrderDetailActivity.this, url.split("=")[1] + "", name);
                return true;
            }
        });
    }

    public void onClickReturn(View view) {
        finish();
    }
}
