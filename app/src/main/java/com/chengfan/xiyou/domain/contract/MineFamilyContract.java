package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyMemberEntity;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/13:34
 * @Description:
 */
public interface MineFamilyContract {
    interface Model {
        Observable<MineFamilyEntity> FAMILY_ENTITY_OBSERVABLE();

        Observable<List<MineFamilyMemberEntity>> FAMILY_MEMBER_OBSERVABLE(int id, int page);

    }


    interface View extends BaseView {
        void familyLoad(MineFamilyEntity familyEntity);

        void familyErrorLoad();

        void familyMemberLoad(List<MineFamilyMemberEntity> familyMemberEntityList, boolean isPtr);
    }

    interface Presenter {
        void familyParameter();

        void familyMemberParameter(int id, int page, boolean isPtr);
    }
}
