package com.chengfan.xiyou.ui.complete;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FontHelper;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/14:56
 * @Description: 选择性别
 */
public class CompleteSexActivity extends BaseActivity {
    @BindView(R.id.complete_sex_title_tv)
    TextView mCompleteSexTitleTv;
    @BindView(R.id.complete_nan_select_iv)
    ImageView mCompleteNanSelectIv;
    @BindView(R.id.complete_nv_select_iv)
    ImageView mCompleteNvSelectIv;
    @BindView(R.id.complete_nan_ll)
    LinearLayout mCompleteNanLl;

    String selectStr;
    @BindView(R.id.complete_nan_iv)
    ImageView mCompleteNanIv;
    @BindView(R.id.complete_z_nan_tv)
    TextView mCompleteZNanTv;
    @BindView(R.id.complete_nv_iv)
    ImageView mCompleteNvIv;
    @BindView(R.id.complete_z_nv_tv)
    TextView mCompleteZNvTv;
    @BindView(R.id.complete_nv_ll)
    LinearLayout mCompleteNvLl;
    @BindView(R.id.complete_nex_btn)
    Button mCompleteNexBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sex);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mCompleteNanLl.setClickable(true);
        mCompleteNanSelectIv.setVisibility(View.VISIBLE);
        mCompleteNvSelectIv.setVisibility(View.GONE);
        selectStr = "1";
        AppData.putString(AppData.Keys.AD_SELECT_SEX, selectStr);
        FontHelper.applyFont(this, mCompleteSexTitleTv, "fonts/Bold.ttf");
        FontHelper.applyFont(this, mCompleteZNanTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteNvIv, APPContents.FONTS_REGULAR);
    }

    @OnClick({R.id.complete_nan_ll, R.id.complete_nv_ll, R.id.complete_nex_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete_nan_ll:
                mCompleteNanSelectIv.setVisibility(View.VISIBLE);
                mCompleteNvSelectIv.setVisibility(View.GONE);
                selectStr = "1";
                AppData.putString(AppData.Keys.AD_SELECT_SEX, selectStr);
                break;
            case R.id.complete_nv_ll:
                mCompleteNanSelectIv.setVisibility(View.GONE);
                mCompleteNvSelectIv.setVisibility(View.VISIBLE);
                selectStr = "0";
                AppData.putString(AppData.Keys.AD_SELECT_SEX, selectStr);
                break;
            case R.id.complete_nex_btn:

                Logger.d(selectStr);
                ForwardUtil.getInstance(this).forward(CompleteInfoActivity.class);
                break;
        }
    }
}
