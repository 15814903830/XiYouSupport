package com.chengfan.xiyou.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.okhttp.BaseActivity;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends BaseActivity {

    public static final String KEY_URL = "url";

    private ImageView iv_back;
    private WebView webView;
    BaseNiceDialog baseNiceDialog;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mUrl = getIntent().getStringExtra(KEY_URL);
        showLoading();
        initView();
        initEvents();
        initWebSettings();
        webView.loadUrl(mUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebSettings() {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
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

        webView.setWebViewClient(new WebViewClient(){
            // 拦截JavaScript的Alert弹窗。
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                // 加载完成
                if (baseNiceDialog!=null)
                    baseNiceDialog.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("should", url);
                    webView.loadUrl(url);
                return true;
            }
        });

    }





    private void initEvents() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back_web_view);
        webView = findViewById(R.id.web_view);
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        Toast.makeText(this, "加载视频中", Toast.LENGTH_SHORT).show();
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                         baseNiceDialog=dialog;
                    }
                })
                .setOutCancel(false)
                .setWidth(200)
                .setHeight(200)
                .setShowBottom(false)
                .show(getSupportFragmentManager());
    }
}
