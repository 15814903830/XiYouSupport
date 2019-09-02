package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineAddPlayContract;
import com.chengfan.xiyou.domain.model.MineAddPlayModelImpl;
import com.chengfan.xiyou.domain.model.entity.MineAddBean;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:58
 * @Description:
 */
public class MineAddPlayPresenterImpl extends BasePresenter<MineAddPlayContract.View> implements MineAddPlayContract.Presenter {
    MineAddPlayContract.Model mModel;

    public MineAddPlayPresenterImpl() {
        mModel = new MineAddPlayModelImpl();
    }

    @Override
    public void mineAddParameter(MineAddBean bean) {
        append(mModel.MINE_ADD_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.mineAddLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }





    @Override
    public void mineUploadParameter(XYUploadEntity xyUploadEntity) {

        append(mModel.UPLOAD_OBSERVABLE(xyUploadEntity), new NetObserver<UpdateEntity>(this) {
            @Override
            public void onNetNext(UpdateEntity result) {
                mView.uploadLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });

    }


}
