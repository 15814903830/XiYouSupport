package com.chengfan.xiyou.domain.presenter;

import android.renderscript.BaseObj;
import android.util.Log;

import com.chengfan.xiyou.domain.contract.DynamicDetailContract;
import com.chengfan.xiyou.domain.model.DynamicDetailModelImpl;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/18:47
 * @Description:
 */
public class DynamicDetailPresenterImpl extends BasePresenter<DynamicDetailContract.View> implements DynamicDetailContract.Presenter {
    DynamicDetailContract.Model mModel;

    public DynamicDetailPresenterImpl() {
        mModel = new DynamicDetailModelImpl();
    }

    @Override
    public void dynamicDetailParameter(int id) {
        append(mModel.DYNAMIC_DETAIL_OBSERVABLE(id), new NetObserver<DynamicDetailEntity>(this) {
            @Override
            public void onNetNext(DynamicDetailEntity result) {
                mView.dynamicDetailLoad(result);
                Log.e("dynamicDetailParameter0","----------");
            }

            @Override
            public void onNetError(Throwable e) {
                Log.e("dynamicDetailParameter0",e.toString());
            }
        });
    }

    @Override
    public void publishCommentParameter(PublishCommentBean bean, final boolean isNoChild) {
        append(mModel.PUBLISH_COMMENT_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.publishCommentLoad(result, isNoChild);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
