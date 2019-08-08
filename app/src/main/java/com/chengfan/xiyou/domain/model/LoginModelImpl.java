package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.LoginContract;
import com.chengfan.xiyou.domain.model.entity.RongEntity;
import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/10:05
 * @Description: 登录请求
 */
public class LoginModelImpl implements LoginContract.Model {
    @Override
    public Observable<LoginResponse> LOGIN_ENTITY_OBSERVABLE(String userName, String password) {
        return HttpRequest.post(APIContents.LOGIN)
                .params(APPContents.E_USER_NAME, userName)
                .params(APPContents.E_PASSWORD, password)
                .execute(new AdaptResponse<LoginResponse>() {
                });
    }


}
