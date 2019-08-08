package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineAttentionContract;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.domain.model.entity.MineAttentionEntity;
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
 * @DATE : 2019-07-23/00:22
 * @Description:
 */
public class MineAttentionModelImpl implements MineAttentionContract.Model {
    @Override
    public Observable<List<MineAttentionEntity>> ATTENTION_OBSERVABLE(int page) {
        return HttpRequest.get(APIContents.FinanceRecord)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<MineAttentionEntity>>() {
                });

    }


    @Override
    public Observable<BaseApiResponse> MEMBER_SHIP_OBSERVABLE(int memberId) {
        MemberShipBean shipBean = new MemberShipBean();
        shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        shipBean.setMemberId(memberId);

        return HttpRequest.post(APIContents.MEMBER_SHIP)
                .paramsJsonString(new Gson().toJson(shipBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }
}
