package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/23:53
 * @Description: 群组 「{@link com.chengfan.xiyou.ui.chat.ChatGroupFragment}」
 */
public class ChatGroupAdapter extends BaseRVAdapter<ChatGroupEntity, BaseViewHolder> {
    public ChatGroupAdapter(int layoutResId, @Nullable List<ChatGroupEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatGroupEntity item) {
        helper.setText(R.id.chat_friend_name_tv, item.getTeam().getName());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.chat_group_user_pic_civ), APIContents.HOST + "/" + item.getTeam().getAvatarUrl());
    }
}
