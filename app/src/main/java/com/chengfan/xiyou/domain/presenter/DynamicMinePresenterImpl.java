package com.chengfan.xiyou.domain.presenter;

import android.util.Log;

import com.chengfan.xiyou.domain.contract.DynamicMineContract;
import com.chengfan.xiyou.domain.model.DynamicMineModelImpl;
import com.chengfan.xiyou.domain.model.entity.DynamicMineDelBean;
import com.chengfan.xiyou.domain.model.entity.DynamicMineEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/22:05
 * @Description: {@link com.chengfan.xiyou.ui.dynamic.DynamicMineFragment}
 */
public class DynamicMinePresenterImpl extends BasePresenter<DynamicMineContract.View> implements DynamicMineContract.Presenter {

    DynamicMineContract.Model mModel;

    public DynamicMinePresenterImpl() {
        mModel = new DynamicMineModelImpl();
    }

    @Override
    public void dynamicMineParameter(int page, final boolean isPtr) {
        append(mModel.DYNAMIC_MINE_ENTITY_OBSERVABLE(page), new NetObserver<List<DynamicMineEntity>>(this) {
            @Override
            public void onNetNext(List<DynamicMineEntity> result) {
                mView.dynamicMineLoad(result, isPtr);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.e("onNetError",e.toString());

            }
        });
    }


    @Override
    public void dynamicMineDelParameter(DynamicMineDelBean dynamicMineDelBean) {
        append(mModel.DYNAMIC_MINE_DEL_OBSERVABLE(dynamicMineDelBean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.dynamicMineDelLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
