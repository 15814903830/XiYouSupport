package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupInfoBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.chengfan.xiyou.domain.model.entity.UpdateTeamBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/18:00
 * @Description: 群详情
 */
public interface ChatGroupDetailContract {
    interface Model {
        Observable<BaseApiResponse> GOUR_INFO_OBSERVABLE(ChatGroupInfoBean bean);

        Observable<BaseApiResponse> UPDATE_TEAM_OBSERVABLE(UpdateTeamBean teamBean);

        Observable<BaseApiResponse> REMOVE_TEAM_RESPONSE_OBSERVABLE(RemoveTeamBean removeTeamBean);

        Observable<BaseApiResponse> REMOVE_TEAM_MEMBER_RESPONSE_OBSERVABLE(RemoveTeamMemberBean removeTeamMemberBean);

        Observable<ChatGroupDetailEntity> CHAT_GROUP_OBSERVABLE(String teamId);
    }


    interface View extends BaseView {
        void getGroupInfoLoad(BaseApiResponse baseApiResponse);

        void getGroupNameLoad(BaseApiResponse baseApiResponse);


        void removeTeam(BaseApiResponse baseApiResponse);

        void removeTeamMember(BaseApiResponse baseApiResponse);

        void chatGroupLoad(ChatGroupDetailEntity chatGroupEntity);
    }

    interface Presenter {
        void groupInfoParameter(ChatGroupInfoBean bean);

        void groupNameParameter(UpdateTeamBean bean);


        void removeTeamParameter(RemoveTeamBean removeTeamBean);

        void removeTeamMemberParameter(RemoveTeamMemberBean removeTeamMemberBean);

        void chatGroupParameter(String teamId);
    }
}
