package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineFamilyContract;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyMemberEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/13:34
 * @Description:
 */
public class MineFamilyModelImpl implements MineFamilyContract.Model {
    @Override
    public Observable<MineFamilyEntity> FAMILY_ENTITY_OBSERVABLE() {
        return HttpRequest.get(APIContents.MyFamily)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<MineFamilyEntity>() {
                });
    }

    @Override
    public Observable<List<MineFamilyMemberEntity>> FAMILY_MEMBER_OBSERVABLE(int id, int page) {
        return
                HttpRequest.get(APIContents.FamilyMemberList)
                        .params(APPContents.E_ID, id)
                        .params(APPContents.E_PAGE, page)
                        .params(APPContents.E_CURRENT_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                        .execute(new AdaptResponse<List<MineFamilyMemberEntity>>() {
                        });
    }
}
