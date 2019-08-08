package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.domain.model.entity.MemberBean;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/20:58
 * @Description: {@link com.chengfan.xiyou.ui.main.HomeFragment}
 */
public interface HomeContract {
    interface Model {
        Observable<HomeBannerEntity> HOME_PAGE_OBSERVABLE(int areaCode, int page);

        Observable<List<MemberBean>> HOME_PAGE_MORE_OBSERVABLE(int areaCode, int page);
    }

    interface View extends BaseView {
        void homePageLoad(HomeBannerEntity result);

        void homePageMoreLoad(List<MemberBean> memberBeanList);
    }

    interface Presenter {
        void homePageParameter(int areaCode, int page);

        void homePageMoreParameter(int areaCode, int page);
    }
}
