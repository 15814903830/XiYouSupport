package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.GetAccountEntity;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.domain.model.entity.VIPOrderBean;
import com.zero.ci.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/12:11
 * @Description:
 */
public interface MineMemberContract {
    interface Model {

        Observable<GetAccountEntity> ACCOUNT_ENTITY_OBSERVABLE();

        Observable<List<MemberSelectBean>> MEMBER_SELECT_OBSERVABLE();

        Observable<String> VIP_ORDER_OBSERVABLE(VIPOrderBean vipOrderBean);
    }

    interface View extends BaseView {
        void accountLoad(GetAccountEntity accountEntity);

        void memberSelectLoad(List<MemberSelectBean> memberSelectBeanList);

        void vipOrderLoad(String result);
    }

    interface Presenter {
        void accountParameter();

        void memberSelectParameter();

        void vipOrderParameter(VIPOrderBean vipOrderBean);
    }
}
