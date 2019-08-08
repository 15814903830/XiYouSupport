package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/22:15
 * @Description: {@link com.chengfan.xiyou.ui.dynamic.DynamicAttentionFragment}
 */
public interface DynamicAttentionContract {
    interface Model {
        Observable<List<FinanceRecordEntity>> LIST_OBSERVABLE(int page);
    }

    interface View  extends BaseView {
        void listLoad(List<FinanceRecordEntity> financeRecordEntityList, boolean isPtr);
    }

    interface Presenter {
        void listParameter(int page, boolean isPtr);
    }
}
