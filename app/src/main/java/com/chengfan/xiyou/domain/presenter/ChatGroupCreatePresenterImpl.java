package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.model.ChatGroupCreateModelImpl;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/12:58
 * @Description: 创建群组
 */
public class ChatGroupCreatePresenterImpl
        extends BasePresenter<ChatGroupCreateContract.View>
        implements ChatGroupCreateContract.Presenter {
    ChatGroupCreateContract.Model mModel;

    public ChatGroupCreatePresenterImpl() {
        mModel = new ChatGroupCreateModelImpl();
    }

    @Override
    public void createParameter(ChatGroupCreateBean bean) {
        append(mModel.CREATE_RESPONSE_OBSERVABLE(bean), new NetObserver<ChatCreateGroupEntity>(this) {
            @Override
            public void onNetNext(ChatCreateGroupEntity result) {
                mView.createdLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }

    @Override
    public void chatGroupParameter() {
        append(mModel.CHAT_OBSERVABLE(), new NetObserver<List<ChatCreteEntity>>(this) {
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
