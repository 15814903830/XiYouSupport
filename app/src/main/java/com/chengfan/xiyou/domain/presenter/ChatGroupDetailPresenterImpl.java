package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.ChatGroupDetailContract;
import com.chengfan.xiyou.domain.model.ChatGroupDetailModelImpl;
import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupInfoBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.chengfan.xiyou.domain.model.entity.UpdateTeamBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/18:00
 * @Description: 群详情
 */
public class ChatGroupDetailPresenterImpl
        extends BasePresenter<ChatGroupDetailContract.View>
        implements ChatGroupDetailContract.Presenter {
    ChatGroupDetailContract.Model mModel;

    public ChatGroupDetailPresenterImpl() {
        mModel = new ChatGroupDetailModelImpl();
    }


    @Override
    public void groupInfoParameter(ChatGroupInfoBean bean) {
        append(mModel.GOUR_INFO_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.getGroupInfoLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }

    @Override
    public void groupNameParameter(UpdateTeamBean bean) {
        append(mModel.UPDATE_TEAM_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.getGroupNameLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
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
    public void chatGroupParameter(String teamId) {
        append(mModel.CHAT_GROUP_OBSERVABLE(teamId), new NetObserver<ChatGroupDetailEntity>(this) {
            @Override
            public void onNetNext(ChatGroupDetailEntity result) {
                mView.chatGroupLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
