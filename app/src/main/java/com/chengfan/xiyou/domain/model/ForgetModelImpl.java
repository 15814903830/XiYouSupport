package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ForgetContract;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/13:29
 * @Description: 找回密码
 */
public class ForgetModelImpl implements ForgetContract.Model {
    @Override
    public Observable<BaseApiResponse> API_RESPONSE_OBSERVABLE(String phoneNum, String pw, String verificationCode) {
        return HttpRequest.post(APIContents.FIND)
                .params(APPContents.E_ENTERYWAY, phoneNum)
                .params(APPContents.E_PASSWORD, pw)
                .params(APPContents.E_CODE, verificationCode)
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> CODE_RESPONSE_OBSERVABLE(String phoneNum) {
        return HttpRequest.post(APIContents.SEND_CODE)
                .params(APPContents.E_ENTERYWAY, phoneNum)
                .params(APPContents.E_TYPE, 2)
                .params(APPContents.E_MEMBER_ID, null)
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
