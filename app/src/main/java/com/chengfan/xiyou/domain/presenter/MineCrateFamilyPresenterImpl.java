package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineCrateFamilyContract;
import com.chengfan.xiyou.domain.model.MineCrateFamilyModelImpl;
import com.chengfan.xiyou.domain.model.entity.CreateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.UpdateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/15:08
 * @Description:
 */
public class MineCrateFamilyPresenterImpl extends BasePresenter<MineCrateFamilyContract.View> implements MineCrateFamilyContract.Presenter {
    MineCrateFamilyContract.Model mModel;

    public MineCrateFamilyPresenterImpl() {
        mModel = new MineCrateFamilyModelImpl();
    }

    @Override
    public void createFamilyParameter(CreateFamilyBean createFamilyBean) {
        append(mModel.CREATE_FAMILY_OBSERVABLE(createFamilyBean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.createFamilyLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void uploadListParameter(List<XYUploadEntity> xyUploadEntityList) {
        append(mModel.UPLOAD_LIST_OBSERVABLE(xyUploadEntityList), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.uploadListLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void updateFamilyParameter(UpdateFamilyBean bean) {
        append(mModel.UPDATE_FAMILY_OBAERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.updateFamilyLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
