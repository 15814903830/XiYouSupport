package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.zero.ci.base.BaseView;
import com.zero.ci.network.zrequest.response.UploadFile;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/00:22
 * @Description: 视频验证
 */
public interface CompleteVideoContract {
    interface Model {
        Observable<UpdateEntity> API_RESPONSE_OBSERVABLE(UploadFile uploadFile);
    }

    interface View extends BaseView {
        void updateVideoLoad(UpdateEntity updateEntity);
    }

    interface Presenter {
        void updateParameter(UploadFile uploadFile);
    }
}
