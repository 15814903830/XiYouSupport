package com.chengfan.xiyou.domain.model;

import android.util.Log;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyDetailContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.model.entity.CheckLetterBean;
import com.chengfan.xiyou.domain.model.entity.CheckLetterEntity;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.domain.model.entity.SayHiBean;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:33
 * @Description:
 */
public class AccompanyDetailModelImpl implements AccompanyDetailContract.Model {
    @Override
    public Observable<AccompanyDetailEntity> ACCOMPANY_DETAIL_OBSERVABLE(int currentMemberId) {
        Log.e("E_ID",""+currentMemberId);
        Log.e("E_ID",""+AppData.getString(AppData.Keys.AD_USER_ID));
        return HttpRequest.get(APIContents.ACCOMPANY_DETAIL)
                .params(APPContents.E_ID, currentMemberId)
                .params(APPContents.E_CURRENT_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<AccompanyDetailEntity>() {
                });
    }

    @Override
    public Observable<CheckLetterEntity> CHECK_LETTER_OBSERVABLE(int currentMemberId) {

        final CheckLetterBean checkLetterBean = new CheckLetterBean();
        checkLetterBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
        checkLetterBean.setFriendId(currentMemberId);
        return HttpRequest.post(APIContents.CheckPrivateLetter)
                .paramsJsonString(new Gson().toJson(checkLetterBean))
                .execute(new AdaptResponse<CheckLetterEntity>() {
                });

    }

    @Override
    public Observable<String> CHECK_VIP_OBSERVABLE() {
        return HttpRequest.post(APIContents.CheckVIP + AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<String>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> MEMBER_SHIP_OBSERVABLE(int currentMemberId) {
        MemberShipBean shipBean = new MemberShipBean();
        shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        shipBean.setMemberId(currentMemberId);
        return HttpRequest.post(APIContents.MEMBER_SHIP)
                .paramsJsonString(new Gson().toJson(shipBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> SAY_HI_OBSERVABLE(int currentMemberId) {
        SayHiBean sayHiBean = new SayHiBean();
        sayHiBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        sayHiBean.setMemberId(currentMemberId);
        return HttpRequest.post(APIContents.SayHi)
                .paramsJsonString(new Gson().toJson(sayHiBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }
}
