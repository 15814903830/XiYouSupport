package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.utils.Timeutils;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-07/12:09
 * @Description: 全部 {@link com.chengfan.xiyou.ui.mine.order.MineOrderAllFragment}
 */
public class MineTakingOrderAllAdapter extends BaseRVAdapter<MineOrderTakingEntity, BaseViewHolder> {


    TakingRefuseListener mTakingRefuseListener;

    public void setTakingRefuseListener(TakingRefuseListener takingRefuseListener) {
        mTakingRefuseListener = takingRefuseListener;
    }

    TakingTakersListener mTakingTakersListener;

    public void setTakingTakersListener(TakingTakersListener takingTakersListener) {
        mTakingTakersListener = takingTakersListener;
    }

    TakingOngoingListener mTakingOngoingListener;

    public void setTakingOngoingListener(TakingOngoingListener takingOngoingListener) {
        mTakingOngoingListener = takingOngoingListener;
    }

    public MineTakingOrderAllAdapter(int layoutResId, @Nullable List<MineOrderTakingEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MineOrderTakingEntity item) {
        Logger.d("MineOrderAllAdapter ===>>>  convert : " + item.getMember().getAvatarUrl());
        // ImageLoaderManager.getInstance().showImage(helper.getView(R.id.order_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
        helper.setText(R.id.order_state_tv, item.getStatusTag());
        helper.setText(R.id.order_game_name_money_tv, item.getAccompanyPlay().getSubject().getTitle() + ". ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        helper.setText(R.id.order_time_tv, "×" + item.getHour());
        helper.setText(R.id.order_money_tv, "合计：" + item.getHour() * item.getAccompanyPlay().getPrice());

        helper.setText(R.id.order_user_name_tv, item.getMember().getNickname());
        Glide.with(mContext).load(APIContents.HOST+"/"+item.getMember().getAvatarUrl()).into((CircleImageView) helper.getView(R.id.order_user_pic_civ));

        Log.e("convert",""+item.getStatus());
        if (item.getStatus() == 1) {
            helper.getView(R.id.taking_ongoing_tv).setVisibility(View.GONE);
            helper.getView(R.id.tanking_no_start_ll).setVisibility(View.VISIBLE);
            helper.getView(R.id.tanking_ll).setVisibility(View.GONE);
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
        } else if (item.getStatus() == 2) {
            helper.getView(R.id.taking_ongoing_tv).setVisibility(View.VISIBLE);
            helper.getView(R.id.tanking_no_start_ll).setVisibility(View.GONE);
            helper.getView(R.id.tanking_ll).setVisibility(View.GONE);
            helper.getView(R.id.taking_ongoing_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //陪玩结束
                    helper.getView(R.id.taking_ongoing_tv).setVisibility(View.GONE);
                    helper.getView(R.id.taking_refuse_tv).setVisibility(View.GONE);
                    helper.getView(R.id.taking_takers_tv).setVisibility(View.GONE);
                    mTakingOngoingListener.onTakingOngoingListener(helper.getAdapterPosition());
                }
            });
        } else if (item.getStatusTag().equals("待确认")) {
            helper.getView(R.id.tanking_ll).setVisibility(View.VISIBLE);
            helper.getView(R.id.taking_ongoing_tv).setVisibility(View.GONE);
            helper.getView(R.id.tanking_no_start_ll).setVisibility(View.GONE);
            Log.e("FinishTime",item.getFinishTime());
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
            helper.setText(R.id.taking_time_tv,  ""+iss);

        }
    }





    public interface TakingRefuseListener {
        void onTakingRefuseListener(int position);
    }

    public interface TakingTakersListener {
        void onTakingTakersListener(int position);
    }

    public interface TakingOngoingListener {
        void onTakingOngoingListener(int position);
    }
}
