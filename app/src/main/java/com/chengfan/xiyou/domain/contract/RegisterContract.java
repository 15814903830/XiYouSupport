package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.RegisterBean;
import com.chengfan.xiyou.domain.model.response.RegisterResponse;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/11:51
 * @Description: 注册
 */
public interface RegisterContract {
    interface Model {
        Observable<RegisterResponse> REGISTER_RESPONSE_OBSERVABLE(RegisterBean bean);

        Observable<BaseApiResponse> API_RESPONSE_OBSERVABLE(String phoneNum);

    }

    interface View extends BaseView {
        void registerLoad(RegisterResponse response);

        void codeLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void registerParameter(RegisterBean bean);

        void sendCodeParameter(String phoneNum);
    }
}
