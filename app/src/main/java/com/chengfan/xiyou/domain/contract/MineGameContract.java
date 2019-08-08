package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:52
 * @Description:
 */
public interface MineGameContract {
    interface Model {
        Observable<List<MemberIdEntity>> GAME_OBSERVABLE();
    }

    interface View extends BaseView {
        void gamePlayLoad(List<MemberIdEntity> memberIdEntityList);
    }

    interface Presenter {
        void gamePlayParameter();
    }
}
