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
 * @DATE : 2019-07-23/23:48
 * @Description:
 */
public interface MineNotStartedContract {
    interface Model {
        Observable<List<MineOrderPlaceEntity>> PLACE_NO_STARTED_OBSERVABLE(int page);

        Observable<List<MineOrderTakingEntity>> TAKING_NO_STARTED_OBSERVABLE(int page);

        Observable<BaseApiResponse> UPDATE_ORDER_STATUS_OBSERVABLE(UpdateOrderStatusBean bean);
    }

    interface View extends BaseView {
        void placeNoStartedLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr);

        void takingNoStartedLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr);
        void updateOrderStatusLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void placeNoStartedParameter(int page, boolean isPtr);

        void takingNoStartedParameter(int page, boolean isPtr);

        void updateOrderStatusParameter(UpdateOrderStatusBean bean);
    }
}
