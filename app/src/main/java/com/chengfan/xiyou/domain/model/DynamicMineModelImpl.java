package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicMineContract;
import com.chengfan.xiyou.domain.model.entity.DynamicMineDelBean;
import com.chengfan.xiyou.domain.model.entity.DynamicMineEntity;
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
 * @DATE : 2019-07-21/22:05
 * @Description: {@link com.chengfan.xiyou.ui.dynamic.DynamicMineFragment}
 */
public class DynamicMineModelImpl implements DynamicMineContract.Model {
    @Override
    public Observable<List<DynamicMineEntity>> DYNAMIC_MINE_ENTITY_OBSERVABLE(int page) {
        return HttpRequest.get(APIContents.MineList)
                .params(APPContents.E_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<DynamicMineEntity>>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> DYNAMIC_MINE_DEL_OBSERVABLE(DynamicMineDelBean mineDelBean) {
        return HttpRequest.post(APIContents.RemoveNews)
                .paramsJsonString(new Gson().toJson(mineDelBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
