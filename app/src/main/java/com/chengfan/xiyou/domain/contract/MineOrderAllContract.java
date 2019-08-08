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
 * @DATE : 2019-07-23/17:30
 * @Description:
 */
public interface MineOrderAllContract {
    interface Model {
        Observable<List<MineOrderPlaceEntity>> PLACE_ORDER_ALL_OBSERVABLE(int page);

        Observable<List<MineOrderTakingEntity>> TAKING_ORDER_ALL_OBSERVABLE(int page);

        Observable<BaseApiResponse> UPDATE_ORDER_STATUS_OBSERVABLE(UpdateOrderStatusBean bean);

    }

    interface View extends BaseView {
        void placeOrderAllLoad(List<MineOrderPlaceEntity> mineOrderPlaceEntityList, boolean isPtr);

        void takingOrderAllLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr);

        void updateOrderStatusLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void placeOrderAllParameter(int page, boolean isPtr);

        void takingOrderAllParameter(int page, boolean isPtr);

        void updateOrderStatusParameter(UpdateOrderStatusBean bean);
    }
}
