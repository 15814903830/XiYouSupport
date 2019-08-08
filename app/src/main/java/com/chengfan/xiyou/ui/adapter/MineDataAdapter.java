package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.MineDataBean;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-12/20:27
 * @Description: 创建群组 「{@link com.chengfan.xiyou.ui.chat.ChatCreateGroupActivity}」
 */
public class MineDataAdapter
        extends BaseRVAdapter<MineDataBean, BaseViewHolder> {

    public static HashMap<Integer, Boolean> isSelected;

    public MineDataAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void addData(@NonNull Collection<? extends MineDataBean> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size() + getHeaderLayoutCount(), newData.size());
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == newData.size()) {
            notifyDataSetChanged();
        }
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
    protected void convert(final BaseViewHolder helper, MineDataBean item) {
        CheckBox checkBox = helper.getView(R.id.select_checkbox);
        checkBox.setChecked(isSelected.get(helper.getAdapterPosition()));
        helper.itemView.setSelected(isSelected.get(helper.getAdapterPosition()));
        helper.setText(R.id.chat_create_name_tv, item.getName());

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

