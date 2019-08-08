package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.MineAttentionEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/17:24
 * @Description: 我的关注
 */
public class MineAttentionAdapter extends BaseRVAdapter<MineAttentionEntity, BaseViewHolder> {
    public MineAttentionAdapter(int layoutResId, @Nullable List<MineAttentionEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineAttentionEntity item) {
        helper.setText(R.id.attention_name_tv, item.getMember().getNickname());
        if (item.getMember().isIsFans()) {
            helper.getView(R.id.attention_xh_tv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.attention_xh_tv).setVisibility(View.GONE);
        }
    }
}
