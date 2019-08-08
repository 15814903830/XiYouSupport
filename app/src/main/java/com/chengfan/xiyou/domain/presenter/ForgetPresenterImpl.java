package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ForgetContract;
import com.chengfan.xiyou.domain.model.ForgetModelImpl;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/13:29
 * @Description: 找回密码
 */
public class ForgetPresenterImpl
        extends BasePresenter<ForgetContract.View>
        implements ForgetContract.Presenter {

    ForgetContract.Model mModel;

    public ForgetPresenterImpl() {
        mModel = new ForgetModelImpl();
    }

    @Override
    public void findParameter(String phoneNum, String pw, String verificationCode) {
        append(mModel.API_RESPONSE_OBSERVABLE(phoneNum, pw, verificationCode), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.findLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }

    @Override
    public void codeParameter(String phoneNum) {
        append(mModel.CODE_RESPONSE_OBSERVABLE(phoneNum), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.CodeLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }
}
