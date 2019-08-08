package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatAddBean;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-26/01:56
 * @Description:
 */
public interface ChatGroupAddContract {
    interface Model {
        Observable<BaseApiResponse> CHAT_ADD_OBSERVABLE(List<ChatAddBean> chatAddBeanList);

        Observable<List<ChatCreteEntity>> CHAT_OBSERVABLE(String teamId);
    }

    interface View extends BaseView {
        void addLoad(BaseApiResponse result);

        void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList);
    }


    interface Presenter {
        void addParameter(List<ChatAddBean> chatAddBeanList);

        void chatGroupParameter(String teamId);
    }
}
