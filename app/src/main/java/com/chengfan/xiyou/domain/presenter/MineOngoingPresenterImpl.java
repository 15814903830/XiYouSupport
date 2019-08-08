package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineOngoingContract;
import com.chengfan.xiyou.domain.model.MineOngoingModelImpl;
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
 * @DATE : 2019-07-24/00:28
 * @Description:
 */
public class MineOngoingPresenterImpl extends BasePresenter<MineOngoingContract.View> implements MineOngoingContract.Presenter {
    MineOngoingContract.Model mModel;

    public MineOngoingPresenterImpl() {
        mModel = new MineOngoingModelImpl();
    }

    @Override
    public void placeOngoingParameter(int page, final boolean isPtr) {
        append(mModel.PLACE_ONGOING_OBSERVABLE(page), new NetObserver<List<MineOrderPlaceEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderPlaceEntity> result) {
                mView.placeOngoingLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });

    }

    @Override
    public void takingOngoingParameter(int page, final boolean isPtr) {
        append(mModel.TAKING_ONGOING_OBSERVABLE(page), new NetObserver<List<MineOrderTakingEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderTakingEntity> result) {
                mView.takingOngoingLoad(result, isPtr);
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
