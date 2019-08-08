package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyDetailContract;
import com.chengfan.xiyou.domain.model.AccompanyDetailModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.model.entity.CheckLetterEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:33
 * @Description:
 */
public class AccompanyDetailPresenterImpl extends BasePresenter<AccompanyDetailContract.View> implements AccompanyDetailContract.Presenter {
    AccompanyDetailContract.Model mModel;

    public AccompanyDetailPresenterImpl() {
        mModel = new AccompanyDetailModelImpl();
    }

    @Override
    public void accompanyDetailParameter(int currentMemberId) {
        append(mModel.ACCOMPANY_DETAIL_OBSERVABLE(currentMemberId), new NetObserver<AccompanyDetailEntity>(this) {
            @Override
            public void onNetNext(AccompanyDetailEntity result) {
                mView.accompanyDetailLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void checkLetterParameter(int currentMemberId) {
        append(mModel.CHECK_LETTER_OBSERVABLE(currentMemberId), new NetObserver<CheckLetterEntity>(this) {
            @Override
            public void onNetNext(CheckLetterEntity result) {
                mView.checkLetterLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void checkVIPParameter() {
        append(mModel.CHECK_VIP_OBSERVABLE(), new NetObserver<String>(this) {
            @Override
            public void onNetNext(String result) {
                mView.checkVIPLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void memberShipLoad(int currentMemberId, final boolean isLike) {
        append(mModel.MEMBER_SHIP_OBSERVABLE(currentMemberId), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.memberShipLoad(result, isLike);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void sayHiParameter(int currentMemberId) {
        append(mModel.SAY_HI_OBSERVABLE(currentMemberId), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.sayHiLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
