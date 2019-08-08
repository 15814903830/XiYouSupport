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
 * @DATE : 2019-07-06/13:05
 * @Description: 消费记录
 */
public class MineRecordActivity extends BaseActivity {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;

    @BindView(R.id.record_wv)
    WebView mRecordWv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_record);
        ButterKnife.bind(this);

        mXyMiddleTv.setText("消费记录");
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mRecordWv.loadUrl("http://xy.gx11.cn/WapFinance/FinanceRecord?id=" + AppData.getString(AppData.Keys.AD_USER_ID) + "&page=1&limit=20");

    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
