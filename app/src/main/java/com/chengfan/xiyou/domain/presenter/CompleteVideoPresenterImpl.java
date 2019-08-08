package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.CompleteVideoContract;
import com.chengfan.xiyou.domain.model.CompleteVideoModelImpl;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;
import com.zero.ci.network.zrequest.response.UploadFile;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/00:22
 * @Description: 视频验证
 */
public class CompleteVideoPresenterImpl
        extends BasePresenter<CompleteVideoContract.View>
        implements CompleteVideoContract.Presenter {
    CompleteVideoContract.Model mModel;

    public CompleteVideoPresenterImpl() {
        mModel = new CompleteVideoModelImpl();
    }

    @Override
    public void updateParameter(UploadFile uploadFile) {
        append(mModel.API_RESPONSE_OBSERVABLE(uploadFile), new NetObserver<UpdateEntity>(this) {
            @Override
            public void onNetNext(UpdateEntity result) {
                mView.updateVideoLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }
}
