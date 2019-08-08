package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineUnprovedContract;
import com.chengfan.xiyou.domain.model.MineUnprovedModelImpl;
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
 * @DATE : 2019-07-24/00:38
 * @Description:
 */
public class MineUnprovedPresenterImpl extends BasePresenter<MineUnprovedContract.View> implements MineUnprovedContract.Presenter {

    MineUnprovedContract.Model mModel;

    public MineUnprovedPresenterImpl() {
        mModel = new MineUnprovedModelImpl();
    }

    @Override
    public void placeUnprovedParameter(int page, final boolean isPtr) {
        append(mModel.PLACE_UNPROVED_OBSERVABLE(page), new NetObserver<List<MineOrderPlaceEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderPlaceEntity> result) {
                mView.placeUnprovedLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void takingUnprovedParameter(int page, final boolean isPtr) {
        append(mModel.TAKING_UNPROVED_OBSERVABLE(page), new NetObserver<List<MineOrderTakingEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderTakingEntity> result) {
                mView.takingUnprovedLoad(result, isPtr);
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
