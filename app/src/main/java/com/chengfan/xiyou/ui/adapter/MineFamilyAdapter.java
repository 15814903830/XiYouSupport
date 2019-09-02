package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.MineFamilyMemberEntity;
import com.chengfan.xiyou.domain.model.entity.XiYouBean;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-08/18:39
 * @Description: 我的家族 {@link com.chengfan.xiyou.ui.mine.MineFamilyActivity}
 */
public class MineFamilyAdapter extends BaseRVAdapter<MineFamilyEntity.FamilyMemberBean, BaseViewHolder> {
    public MineFamilyAdapter(int layoutResId, @Nullable List<MineFamilyEntity.FamilyMemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineFamilyEntity.FamilyMemberBean item) {
        helper.setText(R.id.family_name_tv, item.getMember().getNickname());
        if (item.getRole()==1) {
            helper.getView(R.id.family_action_tv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.family_action_tv).setVisibility(View.GONE);
        }
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.family_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
    }
}
