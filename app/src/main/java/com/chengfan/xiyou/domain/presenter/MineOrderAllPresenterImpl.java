package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineOrderAllContract;
import com.chengfan.xiyou.domain.model.MineOrderAllModelImpl;
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
 * @DATE : 2019-07-23/17:30
 * @Description:
 */
public class MineOrderAllPresenterImpl extends BasePresenter<MineOrderAllContract.View> implements MineOrderAllContract.Presenter {

    MineOrderAllContract.Model mModel;

    public MineOrderAllPresenterImpl() {
        mModel = new MineOrderAllModelImpl();
    }

    @Override
    public void placeOrderAllParameter(int page, final boolean isPtr) {
        append(mModel.PLACE_ORDER_ALL_OBSERVABLE(page), new NetObserver<List<MineOrderPlaceEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderPlaceEntity> result) {
                mView.placeOrderAllLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void takingOrderAllParameter(int page, final boolean isPtr) {
        append(mModel.TAKING_ORDER_ALL_OBSERVABLE(page), new NetObserver<List<MineOrderTakingEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderTakingEntity> result) {
                mView.takingOrderAllLoad(result, isPtr);
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
