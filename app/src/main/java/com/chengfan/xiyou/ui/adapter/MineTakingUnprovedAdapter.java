package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-07/12:34
 * @Description: 待确认
 */
public class MineTakingUnprovedAdapter extends BaseRVAdapter<MineOrderTakingEntity, BaseViewHolder> {


    public MineTakingUnprovedAdapter(int layoutResId, @Nullable List<MineOrderTakingEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MineOrderTakingEntity item) {
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.order_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
        helper.setText(R.id.order_state_tv, item.getStatusTag());
        helper.setText(R.id.order_game_name_money_tv, item.getAccompanyPlay().getSubject().getTitle() + ". ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        helper.setText(R.id.order_time_tv, "×" + item.getHour());
        helper.setText(R.id.order_money_tv, "合计：" + item.getHour() * item.getAccompanyPlay().getPrice());
        helper.setText(R.id.order_user_name_tv, item.getMember().getNickname());

        if (item.getStatusTag().equals("待确认")) {
            helper.getView(R.id.tanking_ll).setVisibility(View.VISIBLE);
            String time=item.getFinishTime().split("T")[1].split(":")[0];
            int mytime= Integer.parseInt(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            int xitontime=Integer.parseInt(simpleDateFormat.format(date));
            int iss=0;
            if (xitontime>mytime){
                iss=(24-(xitontime-mytime));
            }else {
                iss=(24-(mytime-xitontime));
            }
            helper.setText(R.id.taking_time_tv, ""+iss);


        }



    }


}
