package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-12/20:27
 * @Description: 创建群组 「{@link com.chengfan.xiyou.ui.chat.ChatCreateGroupActivity}」
 */
public class ChatCreateGroupAdapter
        extends BaseRVAdapter<ChatCreteEntity, BaseViewHolder> {

    public static HashMap<Integer, Boolean> isSelected;

    public ChatCreateGroupAdapter(int layoutResId, @Nullable List<ChatCreteEntity> data) {
        super(layoutResId, data);

    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<ChatCreteEntity> data) {
        this.mData = data == null ? new ArrayList<ChatCreteEntity>() : data;
        setNotDoAnimationCount(-1);
        notifyDataSetChanged();

        init();
    }


    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < mData.size(); i++) {
            isSelected.put(i, false);
        }
    }

    private OnItemSelectedListener mSelectedListener;

    public void setSelectedListener(OnItemSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ChatCreteEntity item) {
        CheckBox checkBox = helper.getView(R.id.select_checkbox);
        checkBox.setChecked(isSelected.get(helper.getAdapterPosition()));
        helper.itemView.setSelected(isSelected.get(helper.getAdapterPosition()));
        helper.setText(R.id.chat_create_name_tv, item.getNickname());

        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.chat_create_pic_civ), APIContents.HOST + "/" + item.getAvatarUrl());

        if (mSelectedListener != null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedListener.onItemClick(helper.itemView, helper.getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemSelectedListener {

        void onItemClick(View view, int position);
    }
}

