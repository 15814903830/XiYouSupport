package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/21:45
 * @Description: 游戏陪玩
 */
public class AccompanyGameAdapter extends BaseRVAdapter<AccompanyGameEntity, BaseViewHolder> {
    public AccompanyGameAdapter(int layoutResId, @Nullable List<AccompanyGameEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccompanyGameEntity item) {
        helper.setText(R.id.accompany_game_name_tv, item.getNickname())
                .setText(R.id.accompany_game_money_tv, "￥" + item.getPrice())
                //.setText(R.id.accompany_game_address_tv, item.getAddress())
                //.setText(R.id.accompany_game_age_tv, item.get())
                .setText(R.id.accompany_game_num_tv, item.getTotalFans() + "粉丝");
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.accompany_game_civ), APIContents.HOST + "/" + item.getAvatarUrl());
    }
}
