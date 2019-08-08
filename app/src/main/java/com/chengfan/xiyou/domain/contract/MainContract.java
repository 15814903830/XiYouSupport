package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.RongEntity;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/10:58
 * @Description:
 */
public interface MainContract {
    interface Model {
        Observable<RongEntity> RONG_ENTITY_OBSERVABLE(String userId, String name, String portraitUri);
    }

    interface View extends BaseView {
        void rongLoad(RongEntity rongEntity);
    }

    interface Presenter {
        void rongParameter(String userId, String name, String portraitUri);
    }
}
