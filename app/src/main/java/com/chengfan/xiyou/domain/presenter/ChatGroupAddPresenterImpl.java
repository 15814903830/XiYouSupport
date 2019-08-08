package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ChatGroupAddContract;
import com.chengfan.xiyou.domain.model.ChatGroupAddModelImpl;
import com.chengfan.xiyou.domain.model.entity.ChatAddBean;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-26/01:56
 * @Description:
 */
public class ChatGroupAddPresenterImpl extends BasePresenter<ChatGroupAddContract.View> implements ChatGroupAddContract.Presenter {
    ChatGroupAddContract.Model mModel;

    public ChatGroupAddPresenterImpl() {
        mModel = new ChatGroupAddModelImpl();
    }

    @Override
    public void addParameter(List<ChatAddBean> chatAddBeanList) {
        append(mModel.CHAT_ADD_OBSERVABLE(chatAddBeanList), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.addLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void chatGroupParameter(String teamId) {
        append(mModel.CHAT_OBSERVABLE(teamId), new NetObserver<List<ChatCreteEntity>>(this) {
            @Override
            public void onNetNext(List<ChatCreteEntity> result) {
                mView.chatGroupLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
