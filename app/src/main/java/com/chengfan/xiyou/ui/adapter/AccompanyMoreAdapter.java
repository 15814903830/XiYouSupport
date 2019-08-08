package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyMoreEntity;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-15/21:36
 * @Description: 更多陪玩
 */
public class AccompanyMoreAdapter extends BaseRVAdapter<AccompanyMoreEntity, BaseViewHolder> {

    public AccompanyMoreAdapter(int layoutResId, @Nullable List<AccompanyMoreEntity> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AccompanyMoreEntity item) {

        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_game_iv), APIContents.HOST + "/" + item.getImages());

        helper.setText(R.id.search_game_name_tv, item.getTitle());
        helper.setText(R.id.search_game_money_tv, "￥" + item.getPrice() + "/小时");

    }
}
