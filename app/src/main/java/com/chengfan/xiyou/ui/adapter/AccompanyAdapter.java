package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/10:19
 * @Description:
 */
public class AccompanyAdapter extends BaseRVAdapter<AccompanyEntity, BaseViewHolder> {
    private List<Integer> heightList;

    public AccompanyAdapter(int layoutResId, @Nullable List<AccompanyEntity> data) {
        super(layoutResId, data);
    }

    public void addData(@NonNull Collection<? extends AccompanyEntity> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size() + getHeaderLayoutCount(), newData.size());
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == newData.size()) {
            notifyDataSetChanged();
        }

        heightList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            int height = new Random().nextInt(200) + 200;//[200,400)的随机数
            heightList.add(height);
        }
    }


    @Override
    protected void convert(final BaseViewHolder helper, AccompanyEntity item) {

        ViewGroup.LayoutParams params = helper.getView(R.id.ac_img_riv).getLayoutParams();
        params.height = heightList.get(helper.getAdapterPosition());
        helper.getView(R.id.ac_img_riv).setLayoutParams(params);

        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.ac_img_riv), APIContents.HOST + "/" + item.getImages());
        helper.setText(R.id.tv_subject_tv, item.getTitle())
                .setText(R.id.tv_name, item.getNickname());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.cirv_user_pic_civ), APIContents.HOST + "/" + item.getAvatarUrl());
        if (item.getSubjectId().equals("1")) {
            helper.setText(R.id.tv_yxpw, "游戏陪玩");
            helper.setText(R.id.jiage, "￥" + item.getPrice() + "/小时");
        } else {
            helper.getView(R.id.ll_dibu).setVisibility(View.GONE);
        }


    }
}
