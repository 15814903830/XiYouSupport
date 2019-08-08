package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/12:58
 * @Description: 创建群组
 */
public interface ChatGroupCreateContract {
    interface Model {
        Observable<ChatCreateGroupEntity> CREATE_RESPONSE_OBSERVABLE(ChatGroupCreateBean bean);

        Observable<List<ChatCreteEntity>> CHAT_OBSERVABLE();
    }

    interface View extends BaseView {
        void createdLoad(ChatCreateGroupEntity baseApiResponse);

        void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList);
    }

    interface Presenter {
        void createParameter(ChatGroupCreateBean bean);

        void chatGroupParameter();
    }
}
