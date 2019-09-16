package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.DynamicIssuedContract;
import com.chengfan.xiyou.domain.model.DynamicIssuedModelImpl;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;
import com.zero.ci.network.zrequest.response.UploadFile;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/21:17
 * @Description:
 */
public class DynamicIssuedPresenterImpl extends BasePresenter<DynamicIssuedContract.View> implements DynamicIssuedContract.Presenter {
    DynamicIssuedContract.Model mModel;

    public DynamicIssuedPresenterImpl() {
        mModel = new DynamicIssuedModelImpl();
    }

    @Override
    public void publishParameter(String content, List<UploadEntity> fileList) {
        append(mModel.PUBLIST_OBSERVABLE(content, fileList), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.publistLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void uploadParameter(UploadFile fileList,int type) {
        append(mModel.UPLOAD_OBSERVABLE(fileList,type), new NetObserver<UploadEntity>(this) {
            @Override
            public void onNetNext(UploadEntity result) {
                mView.uploadLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
