package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.DynamicIssuedContract;
import com.chengfan.xiyou.domain.model.entity.PublishMemberBean;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.chengfan.xiyou.utils.StringToListUtil;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.widget.logger.Logger;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/21:17
 * @Description:
 */
public class DynamicIssuedModelImpl implements DynamicIssuedContract.Model {
    @Override
    public Observable<BaseApiResponse> PUBLIST_OBSERVABLE(String content, List<UploadEntity> fileList) {
        PublishMemberBean publishMemberBean = new PublishMemberBean();
        publishMemberBean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
        publishMemberBean.setContent(content);
        publishMemberBean.setImages(StringToListUtil.listToString(fileList));
        Logger.d(new Gson().toJson(publishMemberBean));
        return HttpRequest.post(APIContents.PublishMemberNews)
                .paramsJsonString(new Gson().toJson(publishMemberBean))
                .execute(new AdaptResponse<BaseApiResponse>() {
                });

    }

    @Override
    public Observable<UploadEntity> UPLOAD_OBSERVABLE(UploadFile uploadFile) {
        XYUploadEntity xyUploadEntity = new XYUploadEntity();
        xyUploadEntity.setFileData(FileToBase64.best64(uploadFile));
        xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
        xyUploadEntity.setFileName(uploadFile.getKey());
        xyUploadEntity.setSource("Member");

        Logger.d("DynamicIssuedModelImpl ===>>>" + uploadFile.getKey());

        return HttpRequest.post(APIContents.UPLOAD_FILE)
                .paramsJsonString(new Gson().toJson(xyUploadEntity))
                .execute(new AdaptResponse<UploadEntity>() {
                });

    }
}
