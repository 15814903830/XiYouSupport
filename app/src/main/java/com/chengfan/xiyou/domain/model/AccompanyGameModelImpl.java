package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyGameContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/16:08
 * @Description:
 */
public class AccompanyGameModelImpl implements AccompanyGameContract.Model {
    @Override
    public Observable<List<AccompanyGameEntity>> LIST_OBSERVABLE(String subjectId, int page) {
        return HttpRequest.get(APIContents.PLAY_LIST)
                .params(APPContents.E_SUBJECT_ID, subjectId)
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, "20")
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<AccompanyGameEntity>>() {
                });

    }
}
