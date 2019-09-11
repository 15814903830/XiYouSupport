package com.chengfan.xiyou.ui.login;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.LoginContract;
import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.chengfan.xiyou.domain.presenter.LoginPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.ui.complete.CompleteSexActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.utils.UserStorage;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-02/10:55
 */
public class LoginActivity
        extends BaseActivity<LoginContract.View, LoginPresenterImpl>
        implements LoginContract.View{
    @BindView(R.id.login_user_name_et)
    EditText mLoginUserNameEt;
    @BindView(R.id.login_pw_et)
    EditText mLoginPwEt;
    @BindView(R.id.login_display_pw_cb)
    CheckBox mLoginDisplayPwCb;
    @BindView(R.id.login_title_tv)
    TextView mLoginTitleTv;
    @BindView(R.id.login_z_pw_tv)
    TextView mLoginZPwTv;
    @BindView(R.id.login_z_find_tv)
    TextView mLoginZFindTv;
    @BindView(R.id.login_go_reg_tv)
    TextView mLoginGoRegTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        FontHelper.applyFont(this, mLoginTitleTv, APPContents.FONTS_BOLD);
        FontHelper.applyFont(this, mLoginZFindTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mLoginZPwTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mLoginGoRegTv, APPContents.FONTS_BOLD);

    }

    private void initListener() {
        mLoginDisplayPwCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mLoginPwEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mLoginPwEt.setSelection(mLoginPwEt.getText().length());
                } else {
                    mLoginPwEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mLoginPwEt.setSelection(mLoginPwEt.getText().length());
                }
            }
        });
    }

    @Override
    public void loginLoad(LoginResponse loginResponse) {
        Log.e("loginResponse",loginResponse.getMsg());
        if (loginResponse!=null){
        if (loginResponse.isSuc()) {
            APPContents.ID = "" + loginResponse.getData().getId();
            UserStorage.getInstance().saveLoginInfo(loginResponse.getData());

            //设置当前用户信息
            RongIM.getInstance().setCurrentUserInfo(new UserInfo(String.valueOf(loginResponse.getData().getId()),
                    loginResponse.getData().getNickname(),
                    Uri.parse(APIContents.HOST + "/" + loginResponse.getData().getAvatarUrl())));

            AppData.putString(AppData.Keys.AD_USER_ID, loginResponse.getData().getId() + "");
            if (loginResponse.getData().getAge() > 0) {
                ForwardUtil.getInstance(this).forward(MainActivity.class);
            } else {
                ForwardUtil.getInstance(this).forward(CompleteSexActivity.class);
            }

        } else {
            ToastUtil.show(loginResponse.getMsg());
        }

        }else {
            ToastUtil.show("登录失败");
        }
    }

    @OnClick({R.id.login_btn, R.id.login_forget_ll, R.id.login_go_reg_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String userName = mLoginUserNameEt.getText().toString().trim();
                String password = mLoginPwEt.getText().toString().trim();
                if (userName.length() == 0) {
                    ToastUtil.show(R.string.login_phone_txt);
                } else if (password.length() == 0) {
                    ToastUtil.show(R.string.login_pw_txt);
                } else {
                    Log.e("userName",userName);
                    Log.e("userNamepassword",password);
                    mPresenter.loginParameter(userName, password);
                }
                break;
            case R.id.login_forget_ll:
                ForwardUtil.getInstance(this).forward(ForgetActivity.class);
                break;
            case R.id.login_go_reg_tv:
                ForwardUtil.getInstance(this).forward(RegisterActivity.class);
                break;
        }
    }
}
