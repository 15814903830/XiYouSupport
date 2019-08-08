package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.LoginContract;
import com.chengfan.xiyou.domain.model.LoginModelImpl;
import com.chengfan.xiyou.domain.model.entity.RongEntity;
import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/10:05
 * @Description: 登录
 */
public class LoginPresenterImpl
        extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {
    LoginContract.Model mModel;


    public LoginPresenterImpl() {
        mModel = new LoginModelImpl();
    }

    @Override
    public void loginParameter(String userName, String password) {
        append(mModel.LOGIN_ENTITY_OBSERVABLE(userName, password), new NetObserver<LoginResponse>(this) {
            @Override
            public void onNetNext(LoginResponse result) {
                mView.loginLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }


}
