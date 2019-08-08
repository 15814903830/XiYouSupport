package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.ChatGroupContract;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
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
 * @DATE : 2019-07-11/13:27
 * @Description: 群组
 */
public class ChatGroupModelImpl implements ChatGroupContract.Model {
    @Override
    public Observable<BaseApiResponse> REMOVE_TEAM_RESPONSE_OBSERVABLE(RemoveTeamBean removeTeamBean) {
        return HttpRequest.post(APIContents.REMOVE_TEAM)
                .paramsJsonString(new Gson().toJson(removeTeamBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> REMOVE_TEAM_MEMBER_RESPONSE_OBSERVABLE(RemoveTeamMemberBean removeTeamMemberBean) {
        return HttpRequest.post(APIContents.REOMT_TEAM_MEMBER)
                .paramsJsonString(new Gson().toJson(removeTeamMemberBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<List<ChatGroupEntity>> LIST_OBSERVABLE() {
        return HttpRequest.get(APIContents.GROUP + AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<ChatGroupEntity>>() {
                });
    }

}
