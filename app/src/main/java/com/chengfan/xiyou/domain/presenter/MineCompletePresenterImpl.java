package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineCompleteContract;
import com.chengfan.xiyou.domain.model.MineCompleteModelImpl;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/00:47
 * @Description:
 */
public class MineCompletePresenterImpl extends BasePresenter<MineCompleteContract.View> implements MineCompleteContract.Presenter {
    MineCompleteContract.Model mModel;

    public MineCompletePresenterImpl() {
        mModel = new MineCompleteModelImpl();

    }

    @Override
    public void placeCompleteParameter(int page, final boolean isPtr) {
        append(mModel.PLACE_COMPLETE_OBSERVABLE(page), new NetObserver<List<MineOrderPlaceEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderPlaceEntity> result) {
                mView.placeCompleteLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void takingCompleteParameter(int page, final boolean isPtr) {
        append(mModel.TAKING_COMPLETE_OBSERVABLE(page), new NetObserver<List<MineOrderTakingEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderTakingEntity> result) {
                mView.takingCompleteLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void updateOrderStatusParameter(UpdateOrderStatusBean bean) {
        append(mModel.UPDATE_ORDER_STATUS_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.updateOrderStatusLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
