package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.MineMemberContract;
import com.chengfan.xiyou.domain.model.entity.GetAccountEntity;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.domain.model.entity.VIPOrderBean;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;
import com.zero.ci.widget.logger.Logger;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/12:11
 * @Description:
 */
public class MineMemberModelImpl implements MineMemberContract.Model {
    @Override
    public Observable<GetAccountEntity> ACCOUNT_ENTITY_OBSERVABLE() {
        return HttpRequest.get(APIContents.GetAccount + AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<GetAccountEntity>() {
                });
    }

    @Override
    public Observable<List<MemberSelectBean>> MEMBER_SELECT_OBSERVABLE() {
        return HttpRequest.get(APIContents.VipSetMeal)
                .execute(new AdaptResponse<List<MemberSelectBean>>() {
                });
    }


    @Override
    public Observable<String> VIP_ORDER_OBSERVABLE(VIPOrderBean vipOrderBean) {
        return HttpRequest.post(APIContents.CreateVIPOrder)
                .paramsJsonString(new Gson().toJson(vipOrderBean))

                .execute(new AdaptResponse<String>() {
                });
    }
}
