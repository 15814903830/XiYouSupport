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
 * @DATE : 2019-07-07/13:18
 * @Description: 待开始
 */
public class MineTakingNoStartAdapter extends BaseRVAdapter<MineOrderTakingEntity, BaseViewHolder> {
    TakingRefuseListener mTakingRefuseListener;

    public void setTakingRefuseListener(TakingRefuseListener takingRefuseListener) {
        mTakingRefuseListener = takingRefuseListener;
    }

    TakingTakersListener mTakingTakersListener;

    public void setTakingTakersListener(TakingTakersListener takingTakersListener) {
        mTakingTakersListener = takingTakersListener;
    }

    public MineTakingNoStartAdapter(int layoutResId, @Nullable List<MineOrderTakingEntity> data) {
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


        if (item.getStatus() == 1) {
            helper.getView(R.id.tanking_no_start_ll).setVisibility(View.VISIBLE);
            helper.getView(R.id.taking_refuse_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拒绝
                    mTakingRefuseListener.onTakingRefuseListener(helper.getAdapterPosition());
                }
            });

            helper.getView(R.id.taking_takers_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //接单
                    mTakingTakersListener.onTakingTakersListener(helper.getAdapterPosition());
                }
            });
        }

    }

    public interface TakingRefuseListener {
        void onTakingRefuseListener(int position);
    }

    public interface TakingTakersListener {
        void onTakingTakersListener(int position);
    }
}
