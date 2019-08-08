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
import com.chengfan.xiyou.domain.contract.RegisterContract;
import com.chengfan.xiyou.domain.model.entity.RegisterBean;
import com.chengfan.xiyou.domain.model.response.RegisterResponse;
import com.chengfan.xiyou.domain.presenter.RegisterPresenterImpl;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.ui.complete.CompleteSexActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.utils.PhoneNum;
import com.chengfan.xiyou.utils.UserStorage;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ForwardUtil;
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
 * @DATE : 2019-07-02/14:28
 */
public class RegisterActivity
        extends BaseActivity<RegisterContract.View, RegisterPresenterImpl>
        implements RegisterContract.View {
    @BindView(R.id.register_phone_et)
    EditText mRegisterPhoneEt;
    @BindView(R.id.register_verification_et)
    EditText mRegisterVerificationEt;
    @BindView(R.id.register_pw_et)
    EditText mRegisterPwEt;
    @BindView(R.id.register_display_pw_cb)
    CheckBox mLoginDisplayPwCb;
    @BindView(R.id.register_invitation_et)
    EditText mRegisterInvitationEt;
    String phoneNum, verificationCode, pw, invitationCode;
    @BindView(R.id.register_get_code_tv)
    TextView mRegisterGetCodeTv;
    @BindView(R.id.register_have_tv)
    TextView mRegisterHaveTv;
    @BindView(R.id.register_z_title_tv)
    TextView mRegisterZTitleTv;
    @BindView(R.id.register_z_agree_tv)
    TextView mRegisterZAgreeTv;
    @BindView(R.id.register_z_a_tv)
    TextView mRegisterZATv;

    private Disposable mdDisposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        ButterKnife.bind(this);
        initListener();

        FontHelper.applyFont(this, mRegisterZTitleTv, APPContents.FONTS_BOLD);
        FontHelper.applyFont(this, mRegisterZAgreeTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mRegisterZATv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mRegisterHaveTv, APPContents.FONTS_BOLD);


    }

    private void initListener() {
        mLoginDisplayPwCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRegisterPwEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mRegisterPwEt.setSelection(mRegisterPwEt.getText().length());
                } else {
                    mRegisterPwEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mRegisterPwEt.setSelection(mRegisterPwEt.getText().length());
                }
            }
        });

    }

    @OnClick({R.id.register_get_code_tv, R.id.register_submit_btn, R.id.register_have_tv})
    public void onClick(View view) {
        phoneNum = mRegisterPhoneEt.getText().toString().trim();
        verificationCode = mRegisterVerificationEt.getText().toString().trim();
        pw = mRegisterPwEt.getText().toString().trim();
        invitationCode = mRegisterInvitationEt.getText().toString().trim();

        switch (view.getId()) {

            case R.id.register_get_code_tv:
                if (phoneNum.equals("")) {
                    ToastUtil.show(R.string.login_phone_txt);
                } else if (!PhoneNum.isMobileNO(phoneNum)) {
                    ToastUtil.show(R.string.login_check_phone_num_txt);
                } else {
                    mPresenter.sendCodeParameter(phoneNum);
                    mdDisposable = Flowable.intervalRange(0, 51, 0, 1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    mRegisterGetCodeTv.setText("重新获取(" + (50 - aLong) + ")");
                                }
                            })
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Exception {
                                    mRegisterGetCodeTv.setEnabled(true);
                                    mRegisterGetCodeTv.setText("获取验证码");
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
                    RegisterBean bean = new RegisterBean();
                    bean.setEntryway(phoneNum);
                    bean.setCode(verificationCode);
                    bean.setPassword(pw);

                    if (invitationCode.length() > 0)
                        bean.setReference(invitationCode);

                    Logger.d("Register " + new Gson().toJson(bean));
                    mPresenter.registerParameter(bean);
                }
                break;
            case R.id.register_have_tv:
                finish();
                ForwardUtil.getInstance(this).forward(LoginActivity.class);
                break;
        }
    }

    @Override
    public void registerLoad(RegisterResponse response) {
        if (response.isSuc()) {

            UserStorage.getInstance().saveLoginInfo(response.getData());
            AppData.putString(AppData.Keys.AD_USER_ID, response.getData().getId() + "");
            if (response.getData().getAge() > 1) {
                ForwardUtil.getInstance(this).forward(MainActivity.class);
            } else {
                ForwardUtil.getInstance(this).forward(CompleteSexActivity.class);
            }

        } else {
            ToastUtil.show(response.getMsg());
        }
    }

    @Override
    public void codeLoad(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            ToastUtil.show(R.string.register_send_code_txt);
        } else {
            ToastUtil.show(baseApiResponse.getMsg());
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
