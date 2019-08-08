package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.CompleterInfoContract;
import com.chengfan.xiyou.domain.model.CompleterInfoModelImpl;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;
import com.zero.ci.network.zrequest.response.UploadFile;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/22:26
 * @Description: 完善信息
 */
public class CompleterInfoPresenterImpl
        extends BasePresenter<CompleterInfoContract.View>
        implements CompleterInfoContract.Presenter {
    CompleterInfoContract.Model mModel;

    public CompleterInfoPresenterImpl() {
        mModel = new CompleterInfoModelImpl();
    }

    @Override
    public void saveInfoParameter(MemberInfoBean bean) {
        append(mModel.API_RESPONSE_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.saveInfo(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }

    @Override
    public void uploadFileParameter(UploadFile file) {
        append(mModel.UPLOAD_OBSERVABLE(file), new NetObserver<UploadEntity>(this) {
            @Override
            public void onNetNext(UploadEntity result) {
                mView.uploadFile(result);
            }

            @Override
            public void onNetError(Throwable e) {
            }
        });
    }
}
