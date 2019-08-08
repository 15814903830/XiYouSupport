package com.chengfan.xiyou.domain.contract;

import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/13:29
 * @Description: 找回密码
 */
public interface ForgetContract {
    interface Model {
        Observable<BaseApiResponse> API_RESPONSE_OBSERVABLE(String phoneNum, String pw, String verificationCode);

        Observable<BaseApiResponse> CODE_RESPONSE_OBSERVABLE(String phoneNum);
    }

    interface View extends BaseView {
        void findLoad(BaseApiResponse baseApiResponse);

        void CodeLoad(BaseApiResponse baseApiResponse);

    }

    interface Presenter {
        void findParameter(String phoneNum, String pw, String verificationCode);

        void codeParameter(String phoneNum);
    }
}
