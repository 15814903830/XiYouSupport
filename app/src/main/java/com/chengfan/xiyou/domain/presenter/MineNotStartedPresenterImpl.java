package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineNotStartedContract;
import com.chengfan.xiyou.domain.model.MineNotStartedModelImpl;
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
 * @DATE : 2019-07-23/23:48
 * @Description:
 */
public class MineNotStartedPresenterImpl extends BasePresenter<MineNotStartedContract.View> implements MineNotStartedContract.Presenter {
    MineNotStartedContract.Model mModel;

    public MineNotStartedPresenterImpl() {
        mModel = new MineNotStartedModelImpl();
    }

    @Override
    public void placeNoStartedParameter(int page, final boolean isPtr) {
        append(mModel.PLACE_NO_STARTED_OBSERVABLE(page), new NetObserver<List<MineOrderPlaceEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderPlaceEntity> result) {
                mView.placeNoStartedLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void takingNoStartedParameter(int page, final boolean isPtr) {
        append(mModel.TAKING_NO_STARTED_OBSERVABLE(page), new NetObserver<List<MineOrderTakingEntity>>(this) {
            @Override
            public void onNetNext(List<MineOrderTakingEntity> result) {
                mView.takingNoStartedLoad(result, isPtr);
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
