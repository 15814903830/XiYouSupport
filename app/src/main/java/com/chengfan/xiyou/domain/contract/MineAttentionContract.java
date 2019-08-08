package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MineAttentionEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/00:22
 * @Description:
 */
public interface MineAttentionContract {
    interface Model {
        Observable<List<MineAttentionEntity>> ATTENTION_OBSERVABLE(int page);

        Observable<BaseApiResponse> MEMBER_SHIP_OBSERVABLE(int memberId);
    }

    interface View extends BaseView {
        void attentionLoad(List<MineAttentionEntity> attentionEntityList, boolean isPtr);

        void memberShipLoad(BaseApiResponse baseApiResponse);
    }

    interface Presenter {
        void attentionParameter(int page, boolean isPtr);

        void memberShip(int memberId);
    }
}
