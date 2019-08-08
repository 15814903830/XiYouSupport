package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.ChatGroupDetailContract;
import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupInfoBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.chengfan.xiyou.domain.model.entity.UpdateTeamBean;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/18:00
 * @Description: 聊天详情
 */
public class ChatGroupDetailModelImpl implements ChatGroupDetailContract.Model {
    @Override
    public Observable<BaseApiResponse> GOUR_INFO_OBSERVABLE(ChatGroupInfoBean bean) {
        return HttpRequest.post(APIContents.TEAM_MEMBER)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> UPDATE_TEAM_OBSERVABLE(UpdateTeamBean teamBean) {
        return HttpRequest.post(APIContents.UPDATE_TEAM)
                .paramsJsonString(new Gson().toJson(teamBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

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
    public Observable<ChatGroupDetailEntity> CHAT_GROUP_OBSERVABLE(String teamId) {
        return HttpRequest.get(APIContents.TEAM_DETAIL + teamId)
                .execute(new AdaptResponse<ChatGroupDetailEntity>() {
                });

    }
}
