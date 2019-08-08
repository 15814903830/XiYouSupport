package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/13:27
 * @Description: 群组
 */
public interface ChatGroupContract {
    interface Model {
        Observable<List<ChatGroupEntity>> LIST_OBSERVABLE();

        Observable<BaseApiResponse> REMOVE_TEAM_RESPONSE_OBSERVABLE(RemoveTeamBean removeTeamBean);

        Observable<BaseApiResponse> REMOVE_TEAM_MEMBER_RESPONSE_OBSERVABLE(RemoveTeamMemberBean removeTeamMemberBean);

    }

    interface View extends BaseView {
        void removeTeam(BaseApiResponse baseApiResponse);

        void removeTeamMember(BaseApiResponse baseApiResponse);

        void listLoad(List<ChatGroupEntity> chatGroupEntityList);

    }

    interface Presenter {
        void removeTeamParameter(RemoveTeamBean removeTeamBean);

        void removeTeamMemberParameter(RemoveTeamMemberBean removeTeamMemberBean);

        void listLoad();

    }
}
