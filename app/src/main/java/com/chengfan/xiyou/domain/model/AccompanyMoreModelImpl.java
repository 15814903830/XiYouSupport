package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyMoreContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyMoreEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/18:41
 * @Description:
 */
public class AccompanyMoreModelImpl implements AccompanyMoreContract.Model {
    @Override
    public Observable<List<AccompanyMoreEntity>> MORE_OBSERVABLE(String id) {
        return HttpRequest.get(APIContents.ListByMemberId)
                .params(APPContents.E_ID,id)
                .execute(new AdaptResponse<List<AccompanyMoreEntity>>() {
                });

    }
}
