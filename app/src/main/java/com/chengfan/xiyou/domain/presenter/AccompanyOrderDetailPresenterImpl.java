package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyOrderDetailContract;
import com.chengfan.xiyou.domain.model.AccompanyOrderDetailModelImpl;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/19:41
 * @Description:
 */
public class AccompanyOrderDetailPresenterImpl extends BasePresenter<AccompanyOrderDetailContract.View> implements AccompanyOrderDetailContract.Presenter {
    AccompanyOrderDetailContract.Model mModel;

    public AccompanyOrderDetailPresenterImpl() {
        mModel = new AccompanyOrderDetailModelImpl();
    }

    @Override
    public void sendPrivateLetterParameter(String toMemberId) {
        append(mModel.SEND_PRIVATE_LETTER_OBSERVABLE(toMemberId), new NetObserver<String>(this) {
            @Override
            public void onNetNext(String result) {
                mView.sendPrivateLetterLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}

