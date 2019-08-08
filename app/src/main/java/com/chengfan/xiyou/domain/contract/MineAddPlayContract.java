package com.chengfan.xiyou.domain.contract;

import com.chengfan.xiyou.domain.model.entity.MineAddBean;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseView;

import io.reactivex.Observable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/10:58
 * @Description:
 */
public interface MineAddPlayContract {
    interface Model {
        Observable<BaseApiResponse> MINE_ADD_OBSERVABLE(MineAddBean mineAddBean);

        Observable<UpdateEntity> UPLOAD_OBSERVABLE(XYUploadEntity xyUploadEntity);
    }

    interface View extends BaseView {
        void mineAddLoad(BaseApiResponse response);

        void uploadLoad(UpdateEntity response);
    }

    interface Presenter {
        void mineAddParameter(MineAddBean bean);

        void mineUploadParameter(XYUploadEntity xyUploadEntity);
    }
}
