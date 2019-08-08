package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineMemberContract;
import com.chengfan.xiyou.domain.model.MineMemberModelImpl;
import com.chengfan.xiyou.domain.model.entity.GetAccountEntity;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.domain.model.entity.VIPOrderBean;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/12:11
 * @Description:
 */
public class MineMemberPresenterImpl extends BasePresenter<MineMemberContract.View> implements MineMemberContract.Presenter {
    MineMemberContract.Model mModel;

    public MineMemberPresenterImpl() {
        mModel = new MineMemberModelImpl();
    }

    @Override
    public void accountParameter() {
        append(mModel.ACCOUNT_ENTITY_OBSERVABLE(), new NetObserver<GetAccountEntity>(this) {
            @Override
            public void onNetNext(GetAccountEntity result) {
                mView.accountLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }


    @Override
    public void memberSelectParameter() {
        append(mModel.MEMBER_SELECT_OBSERVABLE(), new NetObserver<List<MemberSelectBean>>(this) {
            @Override
            public void onNetNext(List<MemberSelectBean> result) {
                mView.memberSelectLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void vipOrderParameter(VIPOrderBean vipOrderBean) {
        append(mModel.VIP_ORDER_OBSERVABLE(vipOrderBean), new NetObserver<String>(this) {
            @Override
            public void onNetNext(String result) {
                mView.vipOrderLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
