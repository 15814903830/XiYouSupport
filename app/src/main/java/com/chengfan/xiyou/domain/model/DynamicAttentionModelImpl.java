package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicAttentionContract;
import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
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
 * @DATE : 2019-07-21/22:15
 * @Description:
 */
public class DynamicAttentionModelImpl implements DynamicAttentionContract.Model {
    @Override
    public Observable<List<FinanceRecordEntity>> LIST_OBSERVABLE(int page) {
        return HttpRequest.get(APIContents.PageList)
                .params(APPContents.E_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<FinanceRecordEntity>>() {
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
