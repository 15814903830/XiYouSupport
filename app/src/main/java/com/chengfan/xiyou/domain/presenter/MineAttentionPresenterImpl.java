package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineAttentionContract;
import com.chengfan.xiyou.domain.model.MineAttentionModelImpl;
import com.chengfan.xiyou.domain.model.entity.MineAttentionEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/00:22
 * @Description:
 */
public class MineAttentionPresenterImpl extends BasePresenter<MineAttentionContract.View> implements MineAttentionContract.Presenter {
    MineAttentionContract.Model mModel;

    public MineAttentionPresenterImpl() {
        mModel = new MineAttentionModelImpl();
    }

    @Override
    public void attentionParameter(int page, final boolean isPtr) {
        append(mModel.ATTENTION_OBSERVABLE(page), new NetObserver<List<MineAttentionEntity>>(this) {
            @Override
            public void onNetNext(List<MineAttentionEntity> result) {
                mView.attentionLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void memberShip(int memberId) {
        append(mModel.MEMBER_SHIP_OBSERVABLE(memberId), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.memberShipLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
