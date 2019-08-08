package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/10:05
 * @Description: 登录
 */
public interface LoginContract {
    interface Model {
        Observable<LoginResponse> LOGIN_ENTITY_OBSERVABLE(String userName, String password);

    }

    interface View extends BaseView {
        void loginLoad(LoginResponse loginEntity);

    }

    interface Presenter {
        void loginParameter(String userName, String password);

    }
}
