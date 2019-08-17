package com.chengfan.xiyou.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.okhttp.BaseActivity;
import com.tencent.smtt.sdk.WebView;

public class WebActivity extends BaseActivity {

    public static final String KEY_URL = "url";

    private ImageView iv_back;
    private WebView webView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mUrl = getIntent().getStringExtra(KEY_URL);

        initView();
        initEvents();
        webView.loadUrl(mUrl);
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
}
