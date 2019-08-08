package com.chengfan.xiyou.domain.presenter;

import com.chengfan.xiyou.domain.contract.MineGameContract;
import com.chengfan.xiyou.domain.model.MineGameModelImpl;
import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.zero.ci.base.BasePresenter;
import com.zero.ci.base.NetObserver;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:52
 * @Description:
 */
public class MineGamePresenterImpl extends BasePresenter<MineGameContract.View> implements MineGameContract.Presenter {
    MineGameContract.Model mModel;

    public MineGamePresenterImpl() {
        mModel = new MineGameModelImpl();
    }

    @Override
    public void gamePlayParameter() {
        append(mModel.GAME_OBSERVABLE(), new NetObserver<List<MemberIdEntity>>(this) {
            @Override
            public void onNetNext(List<MemberIdEntity> result) {
                mView.gamePlayLoad(result);
            }

            @Override
            public void onNetError(Throwable e) {

            }
        });
    }
}
