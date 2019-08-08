package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ChatGroupContract;
import com.chengfan.xiyou.domain.model.ChatGroupModelImpl;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/13:27
 * @Description: 群组
 */
public class ChatGroupPresenterImpl
        extends BasePresenter<ChatGroupContract.View>
        implements ChatGroupContract.Presenter {

    ChatGroupContract.Model mModel;

    public ChatGroupPresenterImpl() {
        mModel = new ChatGroupModelImpl();
    }

    @Override
    public void removeTeamParameter(RemoveTeamBean removeTeamBean) {
        append(mModel.REMOVE_TEAM_RESPONSE_OBSERVABLE(removeTeamBean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.removeTeam(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void removeTeamMemberParameter(RemoveTeamMemberBean removeTeamMemberBean) {
        append(mModel.REMOVE_TEAM_MEMBER_RESPONSE_OBSERVABLE(removeTeamMemberBean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.removeTeamMember(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void listLoad() {
        append(mModel.LIST_OBSERVABLE(), new NetObserver<List<ChatGroupEntity>>(this) {
            @Override
            public void onNetNext(List<ChatGroupEntity> result) {
                mView.listLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


}
