package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/21:46
 * @Description: {@link com.chengfan.xiyou.ui.main.AccompanyFragment}
 */
public class AccompanyModelImpl implements AccompanyContract.Model {
    @Override
    public Observable<HomeBannerEntity> BANNER_OBSERVABLE(int areaCode, int page) {
        return HttpRequest.get(APIContents.HOME_PAGE)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_AREA_CODE, areaCode)
                .params(APPContents.E_NEWS_CODE, "1100")
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<HomeBannerEntity>() {
                });

    }

    @Override
    public Observable<List<AccompanyEntity>> ACCOMPANY_ENTITY_OBSERVABLE(int page,String position) {
        return HttpRequest.get(APIContents.AccompanyPlayList)
                .params("subjectId", position)
                .params("page", page)
                .params("limit", 20)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<AccompanyEntity>>() {
                });

    }
}
