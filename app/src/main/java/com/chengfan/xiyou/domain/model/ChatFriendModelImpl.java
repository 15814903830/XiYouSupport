package com.chengfan.xiyou.domain.model;

import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatFriendContract;
import com.chengfan.xiyou.domain.model.entity.ChatFriendEntity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AdaptResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:53
 * @Description: 通讯录
 */
public class ChatFriendModelImpl implements ChatFriendContract.Model {

    @Override
    public Observable<BaseApiResponse> RESPONSE_OBSERVABLE(String friendId) {
        return HttpRequest.post(APIContents.REMOVE_FRIEND)
                .params(APPContents.E_MEMBER_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_FRIEND, friendId)
                .execute(new AdaptResponse<BaseApiResponse>() {
                });
    }

    @Override
    public Observable<List<ChatFriendEntity>> FRIEND_OBSERVABLE() {
        return HttpRequest.get(APIContents.ADDRESS_BOOK)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AdaptResponse<List<ChatFriendEntity>>() {
                });

    }
}


