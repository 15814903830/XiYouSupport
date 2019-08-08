package com.chengfan.xiyou.ui.login;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ForgetContract;
import com.chengfan.xiyou.domain.presenter.ForgetPresenterImpl;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.utils.PhoneNum;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/09:42
 */
public class ForgetActivity
        extends BaseActivity<ForgetContract.View, ForgetPresenterImpl>
        implements ForgetContract.View {
    @BindView(R.id.forget_phone_et)
    EditText mForgetPhoneEt;
    @BindView(R.id.forget_verification_et)
    EditText mForgetVerificationEt;
    @BindView(R.id.forget_get_code_tv)
    TextView mForgetGetCodeTv;
    @BindView(R.id.forget_pw_et)
    EditText mForgetPwEt;
    @BindView(R.id.forget_display_pw_cb)
    CheckBox mForgetDisplayPwCb;
    String phoneNum, verificationCode, pw;
    @BindView(R.id.forget_back_btn)
    Button mForgetBackBtn;
    @BindView(R.id.forget_title_tv)
    TextView mForgetTitleTv;
    @BindView(R.id.forget_ll)
    LinearLayout mForgetLl;
    @BindView(R.id.register_submit_btn)
    Button mRegisterSubmitBtn;

    private Disposable mdDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        initListener();

        FontHelper.applyFont(this, mForgetTitleTv, APPContents.FONTS_BOLD);
        FontHelper.applyFont(this, mForgetGetCodeTv, APPContents.FONTS_REGULAR);

    }

    private void initListener() {
        mForgetDisplayPwCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mForgetPwEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mForgetPwEt.setSelection(mForgetPwEt.getText().length());
                } else {
                    mForgetPwEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mForgetPwEt.setSelection(mForgetPwEt.getText().length());
                }
            }
        });
    }

    @Override
    public void findLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void CodeLoad(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            ToastUtil.show(R.string.register_send_code_txt);
        } else {
            ToastUtil.show(baseApiResponse.getMsg());
        }
    }


    @OnClick({R.id.forget_back_btn, R.id.forget_get_code_tv, R.id.register_submit_btn})
    public void onClick(View view) {
        phoneNum = mForgetPhoneEt.getText().toString().trim();
        verificationCode = mForgetVerificationEt.getText().toString().trim();
        pw = mForgetPwEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.forget_back_btn:
                finish();
                break;
            case R.id.forget_get_code_tv:
                if (phoneNum.equals("")) {
                    ToastUtil.show(R.string.login_phone_txt);
                } else if (!PhoneNum.isMobileNO(phoneNum)) {
                    ToastUtil.show(R.string.login_check_phone_num_txt);
                } else {
                    mPresenter.codeParameter(phoneNum);
                    mdDisposable = Flowable.intervalRange(0, 51, 0, 1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    mForgetGetCodeTv.setText("重新获取(" + (50 - aLong) + ")");
                                }
                            })
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Exception {
                                    mForgetGetCodeTv.setEnabled(true);
                                    mForgetGetCodeTv.setText("获取验证码");
                                }
                            })
                            .subscribe();
                }
                break;
            case R.id.register_submit_btn:
                if (phoneNum.equals("")) {
                    ToastUtil.show(R.string.login_phone_txt);
                } else if (!PhoneNum.isMobileNO(phoneNum)) {
                    ToastUtil.show(R.string.login_check_phone_num_txt);
                } else if (verificationCode.equals("")) {
                    ToastUtil.show(R.string.login_verification_null_txt);
                } else if (pw.equals("")) {
                    ToastUtil.show(R.string.login_pw_txt);
                } else {
                    mPresenter.findParameter(phoneNum, pw, verificationCode);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
}
