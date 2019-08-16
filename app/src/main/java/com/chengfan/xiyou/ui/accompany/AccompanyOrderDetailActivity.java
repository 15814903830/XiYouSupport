package com.chengfan.xiyou.ui.accompany;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyOrderDetailContract;
import com.chengfan.xiyou.domain.presenter.AccompanyOrderDetailPresenterImpl;
import com.chengfan.xiyou.ui.chat.WebActivity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/19:38
 * @Description: 订单详情
 */
public class AccompanyOrderDetailActivity extends BaseActivity<AccompanyOrderDetailContract.View, AccompanyOrderDetailPresenterImpl> implements AccompanyOrderDetailContract.View {

    @BindView(R.id.order_detail_wv)
    WebView mOrderDetailWv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order_detail);
        ButterKnife.bind(this);
        initWebSettings();
        initView();
        mOrderDetailWv.loadUrl("http://xy.gx11.cn/WapFinance/AccompanyPlayOrderDetail?id="+ APPContents.DDID +"&memberId="+ AppData.getString(AppData.Keys.AD_USER_ID));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        mOrderDetailWv.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         Log.e("should", url);
                                         if (url.contains("Api/Member/SendPrivateLetter")) {
                                             Toast.makeText(AccompanyOrderDetailActivity.this, "功能待开发", Toast.LENGTH_SHORT).show();
                                         }
                                         return true;
                                     }
                                 });

    }


    @Override
    public void sendPrivateLetterLoad(String result) {

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebSettings() {
        WebSettings webSettings = mOrderDetailWv.getSettings();
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
