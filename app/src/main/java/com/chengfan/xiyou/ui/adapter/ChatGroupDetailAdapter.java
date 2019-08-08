package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/19:32
 * @Description: 群详情---》》 成员
 */
public class ChatGroupDetailAdapter extends BaseRVAdapter<ChatGroupDetailEntity.TeamMemberBeanX, BaseViewHolder> {
    public ChatGroupDetailAdapter(int layoutResId, @Nullable List<ChatGroupDetailEntity.TeamMemberBeanX> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatGroupDetailEntity.TeamMemberBeanX item) {
        helper.setText(R.id.cgb_name_tv, item.getTeamMember().getMember().getNickname());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.cgd_user_pic_civ), APIContents.HOST + "/" + item.getTeamMember().getMember().getAvatarUrl());
        if (item.getTeamMember().getRole() == 1) {
            helper.getView(R.id.cgb_action_tv).setVisibility(View.VISIBLE);
        }
    }

}
