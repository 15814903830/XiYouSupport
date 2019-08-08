package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.HomeContract;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.domain.model.entity.MemberBean;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/20:58
 * @Description:
 */
public class HomeModelImpl implements HomeContract.Model {
    @Override
    public Observable<HomeBannerEntity> HOME_PAGE_OBSERVABLE(int areaCode, int page) {
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
    public Observable<List<MemberBean>> HOME_PAGE_MORE_OBSERVABLE(int areaCode, int page) {
        return HttpRequest.get(APIContents.HOME_PAGE_MORE)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_AREA_CODE, areaCode)
                .params(APPContents.E_NEWS_CODE, "1100")
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AdaptResponse<List<MemberBean>>() {
                });
    }
}
