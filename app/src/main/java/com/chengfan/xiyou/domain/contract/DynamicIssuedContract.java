package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;
import com.zero.ci.network.zrequest.response.UploadFile;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/21:17
 * @Description:
 */
public interface DynamicIssuedContract {
    interface Model {
        Observable<BaseApiResponse> PUBLIST_OBSERVABLE(String content, List<UploadEntity> fileList);

        Observable<UploadEntity> UPLOAD_OBSERVABLE(UploadFile uploadFile,int type);
    }

    interface View extends BaseView {
        void publistLoad(BaseApiResponse baseApiResponse);

        void uploadLoad(UploadEntity result);
    }

    interface Presenter {
        void publishParameter(String content, List<UploadEntity> fileList);

        void uploadParameter(UploadFile fileList,int type);
    }
}
