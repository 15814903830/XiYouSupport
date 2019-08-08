package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/16:08
 * @Description:
 */
public interface AccompanyGameContract {
    interface Model {
        Observable<List<AccompanyGameEntity>> LIST_OBSERVABLE(String subjectId, int page);
    }

    interface View extends BaseView {
        void gameLoad(List<AccompanyGameEntity> gameEntityList, boolean isPtr);
    }

    interface Presenter {
        void gameParameter(String subjectId, int page, boolean isPtr);
    }
}
