package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatFriendEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:53
 * @Description: 通讯 {@link com.chengfan.xiyou.ui.chat.ChatFriendFragment}
 */
public interface ChatFriendContract {
    interface Model {

        Observable<BaseApiResponse> RESPONSE_OBSERVABLE(String friendId);

        Observable<List<ChatFriendEntity>> FRIEND_OBSERVABLE();
    }

    interface View extends BaseView {

        void removeFriend(BaseApiResponse baseApiResponse);

        void getFriendList(List<ChatFriendEntity> chatFriendEntityList);

    }

    interface Presenter {

        void removeParameter(String friendId);

        void friendListParameter();

    }
}
