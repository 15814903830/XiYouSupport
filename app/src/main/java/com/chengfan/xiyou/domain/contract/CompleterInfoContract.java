package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;
import com.zero.ci.network.zrequest.response.UploadFile;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/22:26
 * @Description: 完善信息
 */
public interface CompleterInfoContract {
    interface Model {

        Observable<BaseApiResponse> API_RESPONSE_OBSERVABLE(MemberInfoBean bean);

        Observable<UploadEntity> UPLOAD_OBSERVABLE(UploadFile uploadFile);
    }

    interface View extends BaseView {
        void saveInfo(BaseApiResponse baseApiResponse);

        void uploadFile(UploadEntity result);
    }

    interface Presenter {
        void saveInfoParameter(MemberInfoBean bean);


        void uploadFileParameter(UploadFile file);
    }
}
