package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineFamilyContract;
import com.chengfan.xiyou.domain.model.MineFamilyModelImpl;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyMemberEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/13:34
 * @Description:
 */
public class MineFamilyPresenterImpl extends BasePresenter<MineFamilyContract.View> implements MineFamilyContract.Presenter {
    MineFamilyContract.Model mModel;


    public MineFamilyPresenterImpl() {
        mModel = new MineFamilyModelImpl();
    }

    @Override
    public void familyParameter() {
        append(mModel.FAMILY_ENTITY_OBSERVABLE(), new NetObserver<MineFamilyEntity>(this) {
            @Override
            public void onNetNext(MineFamilyEntity result) {
                mView.familyLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
                mView.familyErrorLoad();
            }
        });
    }

    @Override
    public void familyMemberParameter(int id, int page, final boolean isPtr) {
        append(mModel.FAMILY_MEMBER_OBSERVABLE(id, page), new NetObserver<List<MineFamilyMemberEntity>>(this) {
            @Override
            public void onNetNext(List<MineFamilyMemberEntity> result) {
                mView.familyMemberLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
