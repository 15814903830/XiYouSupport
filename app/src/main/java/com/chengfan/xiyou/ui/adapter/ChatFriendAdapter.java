package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ChatFriendEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/20:16
 * @Description: 好友 {@link com.chengfan.xiyou.ui.chat.ChatFriendFragment}
 */
public class ChatFriendAdapter extends BaseRVAdapter<ChatFriendEntity, BaseViewHolder> {
    public ChatFriendAdapter(int layoutResId, @Nullable List<ChatFriendEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatFriendEntity item) {
        helper.setText(R.id.chat_friend_name_tv, item.getNickname());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.chat_friend_user_pic_civ), APIContents.HOST + "/" + item.getAvatarUrl());
    }
}
