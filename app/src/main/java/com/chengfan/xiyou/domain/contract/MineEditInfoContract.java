package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;
import com.zero.ci.network.zrequest.response.UploadFile;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/00:46
 * @Description:
 */
public interface MineEditInfoContract {
    interface Model {
        Observable<GetMemberInfoEntity> MEMBER_INFO_OBSERVABLE();

        Observable<BaseApiResponse> UPLOAD_OBSERVABLE(UploadFile uploadFile);

        Observable<BaseApiResponse> MEMBER_INFO_UPDATE_OBSERVABLE(MemberInfoBean bean);
    }

    interface View extends BaseView {
        void memberInfoLoad(GetMemberInfoEntity getMemberInfoEntity);

        void uploadLoad(BaseApiResponse baseApiResponse);

        void memberInfoUpdateLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void memberInfoParameter();

        void uploadParameter(UploadFile uploadFile);

        void memberInfoUpdateParameter(MemberInfoBean bean);
    }
}
