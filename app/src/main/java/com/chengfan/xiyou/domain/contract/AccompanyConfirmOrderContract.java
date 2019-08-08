package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.AccompanyConfirmOrderBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/23:22
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyConfirmOrderActivity}
 */
public interface AccompanyConfirmOrderContract {
    interface Model {
        Observable<BaseApiResponse> CONFIRM_ORDER_OBSERVABLE(AccompanyConfirmOrderBean confirmOrderBean);
    }

    interface View extends BaseView {
        void AccompanyConfirmOrderLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void AccompanyConfirmOrderParameter(AccompanyConfirmOrderBean bean);
    }
}
