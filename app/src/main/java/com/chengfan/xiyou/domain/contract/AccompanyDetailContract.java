package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.model.entity.CheckLetterEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/15:33
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyDetailActivity}
 */
public interface AccompanyDetailContract {
    interface Model {
        Observable<AccompanyDetailEntity> ACCOMPANY_DETAIL_OBSERVABLE(int currentMemberId);

        Observable<CheckLetterEntity> CHECK_LETTER_OBSERVABLE(int currentMemberId);

        Observable<String> CHECK_VIP_OBSERVABLE();

        Observable<BaseApiResponse> MEMBER_SHIP_OBSERVABLE(int currentMemberId);

        Observable<BaseApiResponse> SAY_HI_OBSERVABLE(int currentMemberId);
    }

    interface View extends BaseView {
        void accompanyDetailLoad(AccompanyDetailEntity accompanyDetailEntity);

        void checkLetterLoad(CheckLetterEntity checkLetterEntity);

        void checkVIPLoad(String result);

        void memberShipLoad(BaseApiResponse baseApiResponse, boolean isLike);

        void sayHiLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void accompanyDetailParameter(int currentMemberId);

        void checkLetterParameter(int currentMemberId);

        void checkVIPParameter();

        void memberShipLoad(int currentMemberId, boolean isLike);

        void sayHiParameter(int currentMemberId);
    }
}
