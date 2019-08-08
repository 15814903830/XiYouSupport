package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.SayHiEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/12:48
 * @Description:
 */
public class SayHiAdapter extends BaseRVAdapter<SayHiEntity, BaseViewHolder> {
    public SayHiAdapter(int layoutResId, @Nullable List<SayHiEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SayHiEntity item) {
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.user_pic_civ), APIContents.HOST + "/" + item.getAvatarUrl());
    }
}
