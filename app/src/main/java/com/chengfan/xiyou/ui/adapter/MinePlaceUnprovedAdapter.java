package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

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
 * @DATE : 2019-07-07/12:34
 * @Description: 待确认
 */
public class MinePlaceUnprovedAdapter extends BaseRVAdapter<MineOrderPlaceEntity, BaseViewHolder> {

    OrderUnprovedListener mOrderUnprovedListener;

    public void setOrderUnprovedListener(OrderUnprovedListener orderUnprovedListener) {
        mOrderUnprovedListener = orderUnprovedListener;
    }

    public MinePlaceUnprovedAdapter(int layoutResId, @Nullable List<MineOrderPlaceEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MineOrderPlaceEntity item) {
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.order_user_pic_civ), APIContents.HOST + "/" + item.getAccompanyPlay().getMember().getAvatarUrl());
        helper.setText(R.id.order_state_tv, item.getStatusTag());
        helper.setText(R.id.order_game_name_money_tv, item.getAccompanyPlay().getSubject().getTitle() + ". ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        helper.setText(R.id.order_time_tv, "×" + item.getHour());
        helper.setText(R.id.order_money_tv, "合计：" + item.getHour() * item.getAccompanyPlay().getPrice());


        if (item.getStatus() == 4) {
            helper.getView(R.id.place_ll).setVisibility(View.VISIBLE);
            helper.setText(R.id.place_time_tv, item.getFinishTime() + " ");
            helper.getView(R.id.order_unproved_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //确认结束
                    mOrderUnprovedListener.onOrderUnprovedListener(helper.getAdapterPosition());
                }
            });
        }


    }

    public interface OrderUnprovedListener {
        void onOrderUnprovedListener(int position);
    }
}
