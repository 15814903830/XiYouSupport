package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.DynamicDetailContract;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/18:47
 * @Description:
 */
public class DynamicDetailModelImpl implements DynamicDetailContract.Model {
    @Override
    public Observable<DynamicDetailEntity> DYNAMIC_DETAIL_OBSERVABLE(int id) {
        return HttpRequest.get(APIContents.DynamicDetail + id)
                .params("memberId", AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<DynamicDetailEntity>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> PUBLISH_COMMENT_OBSERVABLE(PublishCommentBean bean) {
        return HttpRequest.post(APIContents.PublishMemberNewsComment)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
