package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/00:38
 * @Description:
 */
public interface MineUnprovedContract {
    interface Model {
        Observable<List<MineOrderPlaceEntity>> PLACE_UNPROVED_OBSERVABLE(int page);

        Observable<List<MineOrderTakingEntity>> TAKING_UNPROVED_OBSERVABLE(int page);

        Observable<BaseApiResponse> UPDATE_ORDER_STATUS_OBSERVABLE(UpdateOrderStatusBean bean);
    }

    interface View extends BaseView {
        void placeUnprovedLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr);

        void takingUnprovedLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr);

        void updateOrderStatusLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void placeUnprovedParameter(int page, boolean isPtr);

        void takingUnprovedParameter(int page, boolean isPtr);

        void updateOrderStatusParameter(UpdateOrderStatusBean bean);
    }
}
