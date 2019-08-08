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
 * @DATE : 2019-07-24/00:28
 * @Description:
 */
public interface MineOngoingContract {
    interface Model {
        Observable<List<MineOrderPlaceEntity>> PLACE_ONGOING_OBSERVABLE(int page);

        Observable<List<MineOrderTakingEntity>> TAKING_ONGOING_OBSERVABLE(int page);

        Observable<BaseApiResponse> UPDATE_ORDER_STATUS_OBSERVABLE(UpdateOrderStatusBean bean);
    }

    interface View extends BaseView {
        void placeOngoingLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr);

        void takingOngoingLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr);

        void updateOrderStatusLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void placeOngoingParameter(int page, boolean isPtr);

        void takingOngoingParameter(int page, boolean isPtr);

        void updateOrderStatusParameter(UpdateOrderStatusBean bean);
    }
}
