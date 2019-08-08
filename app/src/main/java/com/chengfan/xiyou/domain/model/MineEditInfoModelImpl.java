package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineEditInfoContract;
import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.widget.logger.Logger;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/00:46
 * @Description:
 */
public class MineEditInfoModelImpl implements MineEditInfoContract.Model {
    @Override
    public Observable<GetMemberInfoEntity> MEMBER_INFO_OBSERVABLE() {
        return HttpRequest.get(APIContents.GetMemberInfo)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<GetMemberInfoEntity>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> UPLOAD_OBSERVABLE(UploadFile uploadFile) {
        XYUploadEntity xyUploadEntity = new XYUploadEntity();
        xyUploadEntity.setFileData(FileToBase64.best64(uploadFile));
        xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
        xyUploadEntity.setFileName(uploadFile.getKey());
        xyUploadEntity.setSource("Member");

        return HttpRequest.post(APIContents.UPLOAD_FILE)
                .paramsJsonString(new Gson().toJson(xyUploadEntity))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }

    @Override
    public Observable<BaseApiResponse> MEMBER_INFO_UPDATE_OBSERVABLE(MemberInfoBean bean) {
        return HttpRequest.post(APIContents.UPDATE)
                .paramsJsonString(new Gson().toJson(bean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }
}
