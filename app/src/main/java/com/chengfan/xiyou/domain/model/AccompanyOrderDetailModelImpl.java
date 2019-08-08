package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.AccompanyOrderDetailContract;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/19:41
 * @Description:
 */
public class AccompanyOrderDetailModelImpl implements AccompanyOrderDetailContract.Model {
    @Override
    public Observable<String> SEND_PRIVATE_LETTER_OBSERVABLE(String toMemberId) {
        return HttpRequest.get(APIContents.SendPrivateLetter + AppData.getString(AppData.Keys.AD_USER_ID))
                .params("toMemberId", toMemberId)
                .execute(new AdaptResponse<String>() {
                });
    }
}
