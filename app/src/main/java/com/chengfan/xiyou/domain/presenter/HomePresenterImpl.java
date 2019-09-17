package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.HomeContract;
import com.chengfan.xiyou.domain.model.HomeModelImpl;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.domain.model.entity.MemberBean;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/20:58
 * @Description:
 */
public class HomePresenterImpl
        extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {
    HomeContract.Model mModel;

    public HomePresenterImpl() {
        mModel = new HomeModelImpl();
    }

    @Override
    public void homePageParameter(int areaCode, int page) {
        append(mModel.HOME_PAGE_OBSERVABLE(areaCode, page), new NetObserver<HomeBannerEntity>(this) {
            @Override
            public void onNetNext(HomeBannerEntity result) {
                mView.homePageLoad(result);
            }
            @Override
            public void onNetError(Throwable e) {

            }
        });
    }

    @Override
    public void homePageMoreParameter(int areaCode, int page) {
        append(mModel.HOME_PAGE_MORE_OBSERVABLE(areaCode, page), new NetObserver<List<MemberBean>>(this) {
            @Override
            public void onNetNext(List<MemberBean> result) {
                mView.homePageMoreLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
