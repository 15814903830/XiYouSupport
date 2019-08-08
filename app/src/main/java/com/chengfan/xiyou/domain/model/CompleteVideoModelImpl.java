package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.CompleteVideoContract;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
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
 * @DATE : 2019-07-04/00:22
 * @Description: 视频验证
 */
public class CompleteVideoModelImpl implements CompleteVideoContract.Model {
    @Override
    public Observable<UpdateEntity> API_RESPONSE_OBSERVABLE(UploadFile uploadFile) {

        XYUploadEntity xyUploadEntity = new XYUploadEntity();
        xyUploadEntity.setFileData(FileToBase64.best64(uploadFile));
        xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
        xyUploadEntity.setFileName(uploadFile.getKey());
        xyUploadEntity.setSource("Member");
        return HttpRequest.post(APIContents.UPLOAD_FILE)
                .paramsJsonString(new Gson().toJson(xyUploadEntity))
                .execute(new AdaptResponse<UpdateEntity>() {
                });
    }
}
