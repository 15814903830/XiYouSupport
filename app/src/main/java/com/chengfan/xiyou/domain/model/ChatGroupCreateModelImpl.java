package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
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
 * @DATE : 2019-07-13/12:58
 * @Description: 创建群组
 */
public class ChatGroupCreateModelImpl implements ChatGroupCreateContract.Model {
    @Override
    public Observable<ChatCreateGroupEntity> CREATE_RESPONSE_OBSERVABLE(ChatGroupCreateBean bean) {
        return HttpRequest.post(APIContents.CREATE_TEAM)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<ChatCreateGroupEntity>() {
                });
    }

    @Override
    public Observable<List<ChatCreteEntity>> CHAT_OBSERVABLE() {
        return HttpRequest.get(APIContents.ADDRESS_BOOK)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<ChatCreteEntity>>() {
                });

    }
}
