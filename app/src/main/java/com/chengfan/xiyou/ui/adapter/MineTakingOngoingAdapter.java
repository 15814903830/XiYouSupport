package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
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
public class MineTakingOngoingAdapter extends BaseRVAdapter<MineOrderTakingEntity, BaseViewHolder> {
    TakingOngoingListener mTakingOngoingListener;

    public void setTakingOngoingListener(TakingOngoingListener takingOngoingListener) {
        mTakingOngoingListener = takingOngoingListener;
    }

    public MineTakingOngoingAdapter(int layoutResId, @Nullable List<MineOrderTakingEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MineOrderTakingEntity item) {
        if (item.getMember().getAvatarUrl() != null)
            ImageLoaderManager.getInstance().showImage(helper.getView(R.id.order_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
        helper.setText(R.id.order_state_tv, item.getStatusTag());
        helper.setText(R.id.order_game_name_money_tv, item.getAccompanyPlay().getSubject().getTitle() + ". ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        helper.setText(R.id.order_time_tv, "×" + item.getHour());
        helper.setText(R.id.order_money_tv, "合计：" + item.getHour() * item.getAccompanyPlay().getPrice());
        helper.setText(R.id.order_user_name_tv, item.getMember().getNickname());

        if (item.getStatus() == 3) {
            helper.getView(R.id.taking_ongoing_tv).setVisibility(View.VISIBLE);
            helper.getView(R.id.taking_ongoing_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //陪玩结束
                    mTakingOngoingListener.onTakingOngoingListener(helper.getAdapterPosition());
                }
            });
        }

    }

    public interface TakingOngoingListener {
        void onTakingOngoingListener(int position);
    }
}
