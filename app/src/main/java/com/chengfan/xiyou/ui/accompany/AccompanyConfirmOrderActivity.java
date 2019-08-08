package com.chengfan.xiyou.ui.accompany;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyConfirmOrderContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyConfirmOrderBean;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyConfirmOrderPresenterImpl;
import com.chengfan.xiyou.domain.presenter.AccompanyPresenterImpl;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/18:56
 * @Description: 确认订单
 */
public class AccompanyConfirmOrderActivity extends BaseActivity<AccompanyConfirmOrderContract.View, AccompanyConfirmOrderPresenterImpl> implements AccompanyConfirmOrderContract.View {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.confirm_game_pic_iv)
    ImageView mConfirmGamePicIv;
    @BindView(R.id.confirm_game_name_tv)
    MediumTextView mConfirmGameNameTv;
    @BindView(R.id.confirm_game_money_tv)
    MediumTextView mConfirmGameMoneyTv;
    @BindView(R.id.confirm_game_time_tv)
    RegularTextView mConfirmGameTimeTv;
    @BindView(R.id.confirm_type_name_tv)
    RegularTextView mConfirmTypeNameTv;
    @BindView(R.id.confirm_tim_tv)
    RegularTextView mConfirmTimTv;
    @BindView(R.id.confirm_heji_time_tv)
    RegularTextView mConfirmHejiTimeTv;
    @BindView(R.id.confirm_heji_money_tv)
    RegularTextView mConfirmHejiMoneyTv;
    @BindView(R.id.confirm_beizhu_et)
    EditText mConfirmBeizhuEt;
    @BindView(R.id.confirm_zfb_select_iv)
    ImageView mConfirmZfbSelectIv;
    @BindView(R.id.confirm_wx_select_iv)
    ImageView mConfirmWxSelectIv;
    @BindView(R.id.confirm_he_tv)
    MediumTextView mConfirmHeTv;

    @BindView(R.id.confirm_zfb_select_rl)
    RelativeLayout mConfirmZfbSelectRl;
    @BindView(R.id.confirm_wx_select_rl)
    RelativeLayout mConfirmWxSelectRl;

    Bundle revBundle;

    AccompanyDetailEntity mAccompanyDetailEntity;

    int time = 1;
    String remarkStr, selectStr, allStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_confirm_order);
        ButterKnife.bind(this);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.confirm_title_txt));

        mAccompanyDetailEntity = new AccompanyDetailEntity();
        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            mAccompanyDetailEntity = (AccompanyDetailEntity) revBundle.getSerializable(APPContents.BUNDLE_FRAGMENT);

            ImageLoaderManager.getInstance().showImage(mConfirmGamePicIv, APIContents.HOST + "/" + mAccompanyDetailEntity.getImages());
            mConfirmGameNameTv.setText(mAccompanyDetailEntity.getNickname());
            mConfirmGameTimeTv.setText(mAccompanyDetailEntity.getWeekDay() + "|" + mAccompanyDetailEntity.getServiceStartTime() + " " + mAccompanyDetailEntity.getServiceEndTime());
            mConfirmTypeNameTv.setText(mAccompanyDetailEntity.getSubjectTitle());
            mConfirmGameMoneyTv.setText("￥" + mAccompanyDetailEntity.getPrice() + "小时");
            mConfirmTimTv.setText(time + "");
            mConfirmHejiTimeTv.setText(time + "");
            mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
            mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");

        }

        mConfirmZfbSelectRl.setClickable(true);
        mConfirmZfbSelectIv.setVisibility(View.VISIBLE);
        mConfirmWxSelectIv.setVisibility(View.GONE);
        selectStr = "3";

    }


    @Override
    public void AccompanyConfirmOrderLoad(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            ForwardUtil.getInstance(this).forward(AccompanyOrderSuccessActivity.class);
        }
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @OnClick({R.id.xy_back_btn, R.id.confirm_time_jian_iv, R.id.confirm_time_jia_iv, R.id.confirm_zfb_select_rl, R.id.confirm_wx_select_rl, R.id.confirm_submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                break;
            case R.id.confirm_time_jian_iv:

                time--;
                if (time <= 0)
                    time = 1;
                mConfirmTimTv.setText(time + "");
                mConfirmHejiTimeTv.setText(time + "");
                mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                break;
            case R.id.confirm_time_jia_iv:


                time++;
                mConfirmTimTv.setText(time + "");
                mConfirmHejiTimeTv.setText(time + "");
                mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");

                break;
            case R.id.confirm_zfb_select_rl:
                selectStr = "3";
                mConfirmWxSelectIv.setVisibility(View.GONE);
                mConfirmZfbSelectIv.setVisibility(View.VISIBLE);
                break;
            case R.id.confirm_wx_select_rl:
                mConfirmZfbSelectIv.setVisibility(View.GONE);
                mConfirmWxSelectIv.setVisibility(View.VISIBLE);
                selectStr = "2";
                break;
            case R.id.confirm_submit_btn:
                allStr = mConfirmHeTv.getText().toString();
                remarkStr = mConfirmBeizhuEt.getText().toString().trim();
                AccompanyConfirmOrderBean confirmOrderBean = new AccompanyConfirmOrderBean();
                confirmOrderBean.setAccompanyPlayId(mAccompanyDetailEntity.getId() + "");
                confirmOrderBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                confirmOrderBean.setHour(time + "");
                confirmOrderBean.setRemark(remarkStr);

                Logger.d("AccompanyConfirmOrderActivity ===>>>  " + new Gson().toJson(confirmOrderBean));
                mPresenter.AccompanyConfirmOrderParameter(confirmOrderBean);
                break;
        }
    }
}
