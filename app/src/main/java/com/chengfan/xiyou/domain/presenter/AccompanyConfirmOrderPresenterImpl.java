package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyConfirmOrderContract;
import com.chengfan.xiyou.domain.model.AccompanyConfirmOrderModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyConfirmOrderBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/23:22
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyConfirmOrderActivity}
 */
public class AccompanyConfirmOrderPresenterImpl extends BasePresenter<AccompanyConfirmOrderContract.View> implements AccompanyConfirmOrderContract.Presenter {
    AccompanyConfirmOrderContract.Model mModel;

    public AccompanyConfirmOrderPresenterImpl() {
        mModel = new AccompanyConfirmOrderModelImpl();
    }

    @Override
    public void AccompanyConfirmOrderParameter(AccompanyConfirmOrderBean bean) {
        append(mModel.CONFIRM_ORDER_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.AccompanyConfirmOrderLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
