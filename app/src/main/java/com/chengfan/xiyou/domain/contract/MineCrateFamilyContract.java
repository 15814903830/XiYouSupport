package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
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

        Observable<ChatCreateGroupEntity> CREATE_RESPONSE_OBSERVABLE(ChatGroupCreateBean bean);
        Observable<List<ChatCreteEntity>> CHAT_OBSERVABLE();
    }

    interface View extends BaseView {
        void createFamilyLoad(BaseApiResponse result);
        void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList);
        void uploadListLoad(BaseApiResponse baseApiResponse);
        void createdLoad(ChatCreateGroupEntity baseApiResponse);
        void updateFamilyLoad(BaseApiResponse result);
    }

    interface Presenter {
        void createFamilyParameter(CreateFamilyBean createFamilyBean);
        void createParameter(ChatGroupCreateBean bean);
        void uploadListParameter(List<XYUploadEntity> xyUploadEntityList);

        void updateFamilyParameter(UpdateFamilyBean bean);
    }
}
