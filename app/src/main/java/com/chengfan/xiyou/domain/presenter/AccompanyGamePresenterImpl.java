package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.AccompanyGameContract;
import com.chengfan.xiyou.domain.model.AccompanyGameModelImpl;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/16:08
 * @Description:
 */
public class AccompanyGamePresenterImpl extends BasePresenter<AccompanyGameContract.View> implements AccompanyGameContract.Presenter {
    AccompanyGameContract.Model mModel;

    public AccompanyGamePresenterImpl() {
        mModel = new AccompanyGameModelImpl();
    }

    @Override
    public void gameParameter(String subjectId, final int page, final boolean isPtr) {
        append(mModel.LIST_OBSERVABLE(subjectId, page), new NetObserver<List<AccompanyGameEntity>>(this) {
            @Override
            public void onNetNext(List<AccompanyGameEntity> result) {
                mView.gameLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
