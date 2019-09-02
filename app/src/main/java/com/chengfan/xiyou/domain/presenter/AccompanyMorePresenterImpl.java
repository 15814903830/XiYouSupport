package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyMoreContract;
import com.chengfan.xiyou.domain.model.AccompanyMoreModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyMoreEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/18:41
 * @Description:
 */
public class AccompanyMorePresenterImpl extends BasePresenter<AccompanyMoreContract.View> implements AccompanyMoreContract.Presenter {
    AccompanyMoreContract.Model mModel;

    public AccompanyMorePresenterImpl() {
        mModel = new AccompanyMoreModelImpl();
    }

    @Override
    public void moreParameter(String id) {
        append(mModel.MORE_OBSERVABLE(id), new NetObserver<List<AccompanyMoreEntity>>(this) {
            @Override
            public void onNetNext(List<AccompanyMoreEntity> result) {
                mView.moreLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
