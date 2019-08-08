package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.AccompanyConfirmOrderContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyConfirmOrderBean;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/23:22
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyConfirmOrderActivity}
 */
public class AccompanyConfirmOrderModelImpl implements AccompanyConfirmOrderContract.Model {

    @Override
    public Observable<BaseApiResponse> CONFIRM_ORDER_OBSERVABLE(AccompanyConfirmOrderBean confirmOrderBean) {

        return HttpRequest.post(APIContents.CreateAccompanyPlayOrder)
                .paramsJsonString(new Gson().toJson(confirmOrderBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
