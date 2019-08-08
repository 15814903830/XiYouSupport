package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-07/12:51
 * @Description: 陪玩中
 */
public class MinePlaceOngoingAdapter extends BaseRVAdapter<MineOrderPlaceEntity, BaseViewHolder> {


    public MinePlaceOngoingAdapter(int layoutResId, @Nullable List<MineOrderPlaceEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MineOrderPlaceEntity item) {
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.order_user_pic_civ), APIContents.HOST + "/" + item.getAccompanyPlay().getMember().getAvatarUrl());
        helper.setText(R.id.order_state_tv, item.getStatusTag());
        helper.setText(R.id.order_game_name_money_tv, item.getAccompanyPlay().getSubject().getTitle() + ". ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        helper.setText(R.id.order_time_tv, "×" + item.getHour());
        helper.setText(R.id.order_money_tv, "合计：" + item.getHour() * item.getAccompanyPlay().getPrice());

    }


}
