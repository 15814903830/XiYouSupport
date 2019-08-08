package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineEditInfoContract;
import com.chengfan.xiyou.domain.model.MineEditInfoModelImpl;
import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;
import com.zero.ci.network.zrequest.response.UploadFile;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/00:46
 * @Description:
 */
public class MineEditInfoPresenterImpl extends BasePresenter<MineEditInfoContract.View> implements MineEditInfoContract.Presenter {
    MineEditInfoContract.Model mModel;

    public MineEditInfoPresenterImpl() {
        mModel = new MineEditInfoModelImpl();
    }

    @Override
    public void memberInfoParameter() {
        append(mModel.MEMBER_INFO_OBSERVABLE(), new NetObserver<GetMemberInfoEntity>(this) {
            @Override
            public void onNetNext(GetMemberInfoEntity result) {
                mView.memberInfoLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void uploadParameter(UploadFile uploadFile) {
        append(mModel.UPLOAD_OBSERVABLE(uploadFile), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.uploadLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void memberInfoUpdateParameter(MemberInfoBean bean) {
        append(mModel.MEMBER_INFO_UPDATE_OBSERVABLE(bean), new NetObserver<BaseApiResponse>(this) {
            @Override
            public void onNetNext(BaseApiResponse result) {
                mView.memberInfoUpdateLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
