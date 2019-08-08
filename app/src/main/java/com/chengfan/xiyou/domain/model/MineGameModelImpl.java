package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineGameContract;
import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:52
 * @Description:
 */
public class MineGameModelImpl implements MineGameContract.Model {
    @Override
    public Observable<List<MemberIdEntity>> GAME_OBSERVABLE() {
        return HttpRequest.get(APIContents.ListByMemberId)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<MemberIdEntity>>() {
                });

    }
}
