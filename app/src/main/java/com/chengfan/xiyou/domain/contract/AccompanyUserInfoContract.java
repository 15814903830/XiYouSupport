package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:00
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity}
 */
public interface AccompanyUserInfoContract {
    interface Model {
        Observable<AccompanyUserInfoEntity> ACCOMPANY_USER_INFO_ENTITY_OBSERVABLE(int currentMemberId);

        Observable<BaseApiResponse> ACCOMPANY_USER_INFO_MEMBER_SHIP_OBSERVABLE(int currentMemberId);
    }

    interface View extends BaseView {
        void userInfoLoad(AccompanyUserInfoEntity accompanyUserInfoEntity);

        void memberShipLoad(BaseApiResponse baseApiResponse, boolean isLike);
    }

    interface Presenter {
        void userInfoParameter(int currentMemberId);

        void memberShipLoad(int currentMemberId, boolean isLike);
    }
}
