package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyContract;
import com.chengfan.xiyou.domain.model.AccompanyModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/21:46
 * @Description: {@link com.chengfan.xiyou.ui.main.AccompanyFragment}
 */
public class AccompanyPresenterImpl extends BasePresenter<AccompanyContract.View> implements AccompanyContract.Presenter {

    AccompanyContract.Model mModel;

    public AccompanyPresenterImpl() {
        mModel = new AccompanyModelImpl();
    }

    @Override
    public void accompanyBannerParameter(int areaCode, int page) {
        append(mModel.BANNER_OBSERVABLE(areaCode, page), new NetObserver<HomeBannerEntity>(this) {
            @Override
            public void onNetNext(HomeBannerEntity result) {
                mView.accompanyBanner(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void accompanyPlayListParameter(final int page, final boolean isPtr,final String position) {
        append(mModel.ACCOMPANY_ENTITY_OBSERVABLE(page,position), new NetObserver<List<AccompanyEntity>>(this) {
            @Override
            public void onNetNext(List<AccompanyEntity> result) {
                mView.accompanyPlayListLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
