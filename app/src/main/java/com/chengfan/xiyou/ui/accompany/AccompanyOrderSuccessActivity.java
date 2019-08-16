package com.chengfan.xiyou.ui.accompany;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/19:57
 * @Description: 下单成功
 */
public class AccompanyOrderSuccessActivity extends BaseActivity {
    private ImageView completeOverIv;
    private RegularTextView completeOverDesTv;
    private MediumTextView successMoneyTv;
    private Button successGoMainBtn;
    private Button successGoDetailBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order_success);
        initView();
        successMoneyTv.setText("$"+ APPContents.TO_ALLSTR);
        successGoMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtil.getInstance(AccompanyOrderSuccessActivity.this).forward(MainActivity.class);
            }
        });

        successGoDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForwardUtil.getInstance(AccompanyOrderSuccessActivity.this).forward(AccompanyOrderDetailActivity.class);
            }
        });
    }

    private void initView() {
        completeOverIv = findViewById(R.id.complete_over_iv);
        completeOverDesTv = findViewById(R.id.complete_over_des_tv);
        successMoneyTv = findViewById(R.id.success_money_tv);
        successGoMainBtn = findViewById(R.id.success_go_main_btn);
        successGoDetailBtn = findViewById(R.id.success_go_detail_btn);
    }

}
