package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/21:46
 * @Description: {@link com.chengfan.xiyou.ui.main.AccompanyFragment}
 */
public interface AccompanyContract {
    interface Model {
        Observable<HomeBannerEntity> BANNER_OBSERVABLE(int areaCode, int page);

        Observable<List<AccompanyEntity>> ACCOMPANY_ENTITY_OBSERVABLE(int page,String position);
    }

    interface View extends BaseView {
        void accompanyBanner(HomeBannerEntity homeBannerEntity);

        void accompanyPlayListLoad(List<AccompanyEntity> accompanyEntityList, boolean isPtr);
    }

    interface Presenter {
        void accompanyBannerParameter(int areaCode, int page);

        void accompanyPlayListParameter(int page, boolean isPtr,String position);

    }
}
