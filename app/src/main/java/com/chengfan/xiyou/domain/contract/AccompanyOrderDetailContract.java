package com.chengfan.xiyou.domain.contract;

import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/19:41
 * @Description:
 */
public interface AccompanyOrderDetailContract {
    interface Model {
        Observable<String> SEND_PRIVATE_LETTER_OBSERVABLE(String toMemberId);
    }

    interface View extends BaseView {
        void sendPrivateLetterLoad(String result);
    }

    interface Presenter {
        void sendPrivateLetterParameter(String toMemberId);
    }
}
