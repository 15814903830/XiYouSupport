package com.chengfan.xiyou.domain.presenter;

import android.util.Log;

import com.chengfan.xiyou.domain.contract.DynamicAttentionContract;
import com.chengfan.xiyou.domain.model.DynamicAttentionModelImpl;
import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/22:15
 * @Description:
 */
public class DynamicAttentionPresenterImpl extends BasePresenter<DynamicAttentionContract.View> implements DynamicAttentionContract.Presenter {
    DynamicAttentionContract.Model mModel;

    public DynamicAttentionPresenterImpl() {
        mModel = new DynamicAttentionModelImpl();
    }

    @Override
    public void listParameter(int page, final boolean isPtr) {
        append(mModel.LIST_OBSERVABLE(page), new NetObserver<List<FinanceRecordEntity>>(this) {
            @Override
            public void onNetNext(List<FinanceRecordEntity> result) {
                mView.listLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.e("Throwable",e.toString());
            }
        });
    }
}
