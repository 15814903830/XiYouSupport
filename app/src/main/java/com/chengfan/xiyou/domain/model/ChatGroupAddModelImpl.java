package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatGroupAddContract;
import com.chengfan.xiyou.domain.model.entity.ChatAddBean;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
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
 * @DATE : 2019-07-26/01:56
 * @Description:
 */
public class ChatGroupAddModelImpl implements ChatGroupAddContract.Model {
    @Override
    public Observable<BaseApiResponse> CHAT_ADD_OBSERVABLE(List<ChatAddBean> chatAddBeanList) {
        return HttpRequest.post(APIContents.AddTeamMemberMore)
                .paramsJsonString(new Gson().toJson(chatAddBeanList))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<List<ChatCreteEntity>> CHAT_OBSERVABLE(String teamId) {
        return HttpRequest.get(APIContents.ADDRESS_BOOK)
                .params("teamId", teamId)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<ChatCreteEntity>>() {
                });
    }
}
