package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyMoreEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/18:41
 * @Description:
 */
public interface AccompanyMoreContract {
    interface Model {
        Observable<List<AccompanyMoreEntity>> MORE_OBSERVABLE(String id);
    }

    interface View extends BaseView {
        void moreLoad(List<AccompanyMoreEntity> accompanyMoreEntityList);
    }

    interface Presenter {
        void moreParameter(String id);
    }
}
