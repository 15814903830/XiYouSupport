package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.CreateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.UpdateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/15:08
 * @Description:
 */
public interface MineCrateFamilyContract {
    interface Model {
        Observable<BaseApiResponse> CREATE_FAMILY_OBSERVABLE(CreateFamilyBean createFamilyBean);

        Observable<BaseApiResponse> UPLOAD_LIST_OBSERVABLE(List<XYUploadEntity> xyUploadEntities);

        Observable<BaseApiResponse> UPDATE_FAMILY_OBAERVABLE(UpdateFamilyBean updateFamilyBean);

    }

    interface View extends BaseView {
        void createFamilyLoad(BaseApiResponse result);

        void uploadListLoad(BaseApiResponse baseApiResponse);

        void updateFamilyLoad(BaseApiResponse result);
    }

    interface Presenter {
        void createFamilyParameter(CreateFamilyBean createFamilyBean);

        void uploadListParameter(List<XYUploadEntity> xyUploadEntityList);

        void updateFamilyParameter(UpdateFamilyBean bean);
    }
}
