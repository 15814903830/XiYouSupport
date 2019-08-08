package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ChatFriendContract;
import com.chengfan.xiyou.domain.model.ChatFriendModelImpl;
import com.chengfan.xiyou.domain.model.entity.ChatFriendEntity;
import com.chengfan.xiyou.domain.model.response.ChatFriendResponse;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:53
 * @Description: 好友列表「通讯录」
 */
public class ChatFriendPresenterImpl
        extends BasePresenter<ChatFriendContract.View>
        implements ChatFriendContract.Presenter {

    ChatFriendContract.Model mModel;

    public ChatFriendPresenterImpl() {
        mModel = new ChatFriendModelImpl();
    }


    @Override
    public void removeParameter(String friendId) {
        append(mModel.RESPONSE_OBSERVABLE(friendId), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.removeFriend(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void friendListParameter() {
        append(mModel.FRIEND_OBSERVABLE(), new NetObserver<List<ChatFriendEntity>>(this) {
            @Override
            public void onNetNext(List<ChatFriendEntity> result) {
                mView.getFriendList(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
