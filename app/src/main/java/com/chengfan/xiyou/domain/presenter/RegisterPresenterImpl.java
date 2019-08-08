package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.RegisterContract;
import com.chengfan.xiyou.domain.model.RegisterModelImpl;
import com.chengfan.xiyou.domain.model.entity.RegisterBean;
import com.chengfan.xiyou.domain.model.response.RegisterResponse;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/11:51
 * @Description: 注册
 */
public class RegisterPresenterImpl
        extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {
    RegisterContract.Model mModel;

    public RegisterPresenterImpl() {
        mModel = new RegisterModelImpl();
    }

    @Override
    public void registerParameter(RegisterBean bean) {
        append(mModel.REGISTER_RESPONSE_OBSERVABLE(bean), new NetObserver<RegisterResponse>(this) {
            @Override
            public void onNetNext(RegisterResponse result) {
                mView.registerLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void sendCodeParameter(String phoneNum) {
        append(mModel.API_RESPONSE_OBSERVABLE(phoneNum), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.codeLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }
}
