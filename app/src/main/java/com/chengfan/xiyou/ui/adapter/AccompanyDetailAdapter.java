package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/12:04
 * @Description:
 */
public class AccompanyDetailAdapter extends BaseRVAdapter<AccompanyDetailEntity.OrderBean, BaseViewHolder> {
    public AccompanyDetailAdapter(int layoutResId, @Nullable List<AccompanyDetailEntity.OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccompanyDetailEntity.OrderBean item) {
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.ac_detail_civ), APIContents.HOST + "/" + item.getAvatarUrl());
        helper.setText(R.id.ac_detail_nick_name_tv, item.getNickname());
        helper.setText(R.id.ac_detail_time_tv, item.getCommentTime());
        helper.setText(R.id.ac_detail_content_tv, item.getComment());

    }
}
