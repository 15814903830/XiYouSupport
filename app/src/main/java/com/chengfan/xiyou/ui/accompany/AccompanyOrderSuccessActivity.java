package com.chengfan.xiyou.ui.accompany;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/19:57
 * @Description: 下单成功
 */
public class AccompanyOrderSuccessActivity extends BaseActivity {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.success_money_tv)
    MediumTextView mSuccessMoneyTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order_success);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

    }

    @OnClick({R.id.success_go_main_btn, R.id.success_go_detail_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.success_go_main_btn:
                ForwardUtil.getInstance(this).forward(MainActivity.class);
                break;
            case R.id.success_go_detail_btn:
                ForwardUtil.getInstance(this).forward(AccompanyOrderDetailActivity.class);
                break;
        }
    }
}
