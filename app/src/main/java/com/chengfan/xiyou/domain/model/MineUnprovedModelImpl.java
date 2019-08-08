package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineUnprovedContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/00:38
 * @Description:
 */
public class MineUnprovedModelImpl implements MineUnprovedContract.Model {
    @Override
    public Observable<List<MineOrderPlaceEntity>> PLACE_UNPROVED_OBSERVABLE(int page) {
        return HttpRequest.get(APIContents.MyCreateOrder)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_ORDER_STATUS, 3)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<MineOrderPlaceEntity>>() {
                });
    }

    @Override
    public Observable<List<MineOrderTakingEntity>> TAKING_UNPROVED_OBSERVABLE(int page) {
        return HttpRequest.get(APIContents.MyAccompanyPlayOrder)
                // .params(APPContents.E_ID, 1)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_ORDER_STATUS, 3)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<MineOrderTakingEntity>>() {
                });
    }


    @Override
    public Observable<BaseApiResponse> UPDATE_ORDER_STATUS_OBSERVABLE(UpdateOrderStatusBean bean) {
        return HttpRequest.post(APIContents.UpdateAccompanyPlayOrderStatus)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
