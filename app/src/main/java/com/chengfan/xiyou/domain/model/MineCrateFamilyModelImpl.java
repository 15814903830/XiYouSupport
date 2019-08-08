package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.MineCrateFamilyContract;
import com.chengfan.xiyou.domain.model.entity.CreateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.UpdateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.network.zrequest.response.AdaptResponse;
import com.zero.ci.widget.logger.Logger;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/15:08
 * @Description:
 */
public class MineCrateFamilyModelImpl implements MineCrateFamilyContract.Model {
    @Override
    public Observable<BaseApiResponse> CREATE_FAMILY_OBSERVABLE(CreateFamilyBean createFamilyBean) {
        return HttpRequest.post(APIContents.CreateFamily)
                .paramsJsonString(new Gson().toJson(createFamilyBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> UPLOAD_LIST_OBSERVABLE(List<XYUploadEntity> xyUploadEntities) {
        return HttpRequest.post(APIContents.UPLOAD_FILE_MORE)
                .paramsJsonString(new Gson().toJson(xyUploadEntities))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<BaseApiResponse> UPDATE_FAMILY_OBAERVABLE(UpdateFamilyBean updateFamilyBean) {
        return HttpRequest.post(APIContents.UpdateFamily)
                .paramsJsonString(new Gson().toJson(updateFamilyBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }
}
