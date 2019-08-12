package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.RegisterContract;
import com.chengfan.xiyou.domain.model.entity.RegisterBean;
import com.chengfan.xiyou.domain.model.response.RegisterResponse;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/11:51
 * @Description: 注册
 */
public class RegisterModelImpl implements RegisterContract.Model {
    @Override
    public Observable<RegisterResponse> REGISTER_RESPONSE_OBSERVABLE(RegisterBean bean) {
        return HttpRequest.post(APIContents.REGISTER)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<RegisterResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> API_RESPONSE_OBSERVABLE(String phoneNum) {
        return HttpRequest.post(APIContents.SEND_CODE)
                .params(APPContents.E_ENTERYWAY, phoneNum)
                .params(APPContents.E_TYPE, 1)
                .params(APPContents.E_MEMBER_ID, null)
                .execute(new AdaptResponse<BaseApiResponse>() {

                });
    }
}
