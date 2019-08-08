package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyUserInfoContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:00
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity}
 */
public class AccompanyUserInfoModelImpl implements AccompanyUserInfoContract.Model {
    @Override
    public Observable<AccompanyUserInfoEntity> ACCOMPANY_USER_INFO_ENTITY_OBSERVABLE(int currentMemberId) {
        return HttpRequest.get(APIContents.ACCOMPANY)
                .params(APPContents.E_ID, currentMemberId)
                .params(APPContents.E_CURRENT_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params("accompanyPlaylimit", "2")
                .params("memberNewslimit", 20)
                .execute(new AdaptResponse<AccompanyUserInfoEntity>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> ACCOMPANY_USER_INFO_MEMBER_SHIP_OBSERVABLE(int currentMemberId) {
        MemberShipBean shipBean = new MemberShipBean();
        shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        shipBean.setMemberId(currentMemberId);
        return HttpRequest.post(APIContents.MEMBER_SHIP)
                .paramsJsonString(new Gson().toJson(shipBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
