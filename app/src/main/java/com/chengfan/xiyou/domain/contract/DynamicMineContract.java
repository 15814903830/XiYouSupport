package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.DynamicMineDelBean;
import com.chengfan.xiyou.domain.model.entity.DynamicMineEntity;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/22:05
 * @Description: {@link com.chengfan.xiyou.ui.dynamic.DynamicMineFragment}
 */
public interface DynamicMineContract {
    interface Model {
        Observable<List<DynamicMineEntity>> DYNAMIC_MINE_ENTITY_OBSERVABLE(int page);

        Observable<BaseApiResponse> DYNAMIC_MINE_DEL_OBSERVABLE(DynamicMineDelBean mineDelBean);

        Observable<BaseApiResponse> PUBLISH_COMMENT_OBSERVABLE(PublishCommentBean bean);
    }

    interface View extends BaseView {
        void dynamicMineLoad(List<DynamicMineEntity> dynamicMineEntityList, boolean isPtr);

        void dynamicMineDelLoad(BaseApiResponse baseApiResponse);

        void publishCommentLoad(BaseApiResponse baseApiResponse, boolean isNoChild);
    }

    interface Presenter {
        void dynamicMineParameter(int page, boolean isPtr);

        void dynamicMineDelParameter(DynamicMineDelBean dynamicMineDelBean);

        void publishCommentParameter(PublishCommentBean bean, boolean isNoChild);
    }
}
