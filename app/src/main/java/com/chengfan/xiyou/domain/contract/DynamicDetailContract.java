package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/18:47
 * @Description:
 */
public interface DynamicDetailContract {
    interface Model {
        Observable<DynamicDetailEntity> DYNAMIC_DETAIL_OBSERVABLE(int id);

        Observable<BaseApiResponse> PUBLISH_COMMENT_OBSERVABLE(PublishCommentBean bean);
    }

    interface View extends BaseView {
        void dynamicDetailLoad(DynamicDetailEntity dynamicDetailEntity);

        void publishCommentLoad(BaseApiResponse baseApiResponse, boolean isNoChild);
    }

    interface Presenter {
        void dynamicDetailParameter(int id);

        void publishCommentParameter(PublishCommentBean bean, boolean isNoChild);
    }
}
