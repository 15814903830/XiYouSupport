package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyUserInfoContract;
import com.chengfan.xiyou.domain.model.AccompanyUserInfoModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:00
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity}
 */
public class AccompanyUserInfoPresenterImpl extends BasePresenter<AccompanyUserInfoContract.View> implements AccompanyUserInfoContract.Presenter {
    AccompanyUserInfoContract.Model mModel;

    public AccompanyUserInfoPresenterImpl() {
        mModel = new AccompanyUserInfoModelImpl();
    }

    @Override
    public void userInfoParameter(int currentMemberId) {
        append(mModel.ACCOMPANY_USER_INFO_ENTITY_OBSERVABLE(currentMemberId), new NetObserver<AccompanyUserInfoEntity>(this) {
            @Override
            public void onNetNext(AccompanyUserInfoEntity result) {
                mView.userInfoLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void memberShipLoad(int bean, final boolean isLike) {
        append(mModel.ACCOMPANY_USER_INFO_MEMBER_SHIP_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.memberShipLoad(result, isLike);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}

