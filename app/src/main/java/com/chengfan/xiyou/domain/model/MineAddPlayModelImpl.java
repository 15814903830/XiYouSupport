package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.MineAddPlayContract;
import com.chengfan.xiyou.domain.model.entity.MineAddBean;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:58
 * @Description:
 */
public class MineAddPlayModelImpl implements MineAddPlayContract.Model {
    @Override
    public Observable<BaseApiResponse> MINE_ADD_OBSERVABLE(MineAddBean mineAddBean) {
        return HttpRequest.post(APIContents.Publish)
                .paramsJsonString(new Gson().toJson(mineAddBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }

    public Observable<BaseApiResponse> MINE_ADD_OBSERVABLEE(MineAddBean mineAddBean) {
        return HttpRequest.post(APIContents.Publish)
                .paramsJsonString(new Gson().toJson(mineAddBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }


    @Override
    public Observable<UpdateEntity> UPLOAD_OBSERVABLE(XYUploadEntity xyUploadEntity) {
        return HttpRequest.post(APIContents.UPLOAD_FILE)
                .paramsJsonString(new Gson().toJson(xyUploadEntity))
                .execute(new AdaptResponse<UpdateEntity>() {
                });

    }
}
